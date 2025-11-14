package com.multisupply.sgi.usuarios.services;

import com.multisupply.sgi.usuarios.entities.dtos.DetallesUsuarioDTO;
import com.multisupply.sgi.usuarios.entities.dtos.UsuarioDTO;
import com.multisupply.sgi.usuarios.entities.enums.RolList;
import com.multisupply.sgi.usuarios.entities.models.Perfil;
import com.multisupply.sgi.usuarios.entities.models.Rol;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.PerfilRepository;
import com.multisupply.sgi.usuarios.repositories.RolRepository;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;
    
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioDTO dto, RolList rolNombre) {
        Rol rol = rolRepository.findByNombre(rolNombre)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado."));
        
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }
        
        Usuario usuario = mapToEntity(dto, rol);
        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }
    
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    
    public Optional<UsuarioDTO> obtenerUsuario(String id) {
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }
    
    public Optional<DetallesUsuarioDTO> obtenerDetallesUsuario(String id) {
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Usuario usuario = usuarioOpt.get();
        
        Optional<Perfil> perfilOpt = perfilRepository
            .findAll()
            .stream()
            .filter(p -> p.getUsuario().getId().equals(id))
            .findFirst();
        
        DetallesUsuarioDTO dto = new DetallesUsuarioDTO();
        
        dto.setIdUsuario(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol().getNombre().getDisplayName());
        dto.setActivo(usuario.isActivo());
        dto.setFechaCreacion(
            usuario.getFechaCreacion() != null
                ? usuario.getFechaCreacion().toString()
                : "-"
        );
        
        dto.setNombreCompleto(perfilOpt.map(Perfil::getNombreCompleto).orElse("-"));
        dto.setDni(perfilOpt.map(Perfil::getDni).orElse("-"));
        dto.setTelefono(perfilOpt.map(Perfil::getTelefono).orElse("-"));
        dto.setEdad(perfilOpt.map(Perfil::getEdad).orElse(null));
        dto.setFechaNacimiento(
            perfilOpt
                .map(Perfil::getFechaNacimiento)
                .map(fecha -> fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .orElse("-")
        );
        dto.setPais(perfilOpt.map(p -> p.getPaises().getDisplayName()).orElse("-"));
        return Optional.of(dto);
    }
    
    @Transactional
    public boolean cambiarEstado(String id) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setActivo(!usuario.isActivo());
                usuarioRepository.save(usuario);
                return true;
            })
            .orElse(false);
    }
    
    private UsuarioDTO mapToDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getUsername(),
            null,
            usuario.getEmail()
        );
    }
    
    private Usuario mapToEntity(UsuarioDTO dto, Rol rol) {
        return new Usuario(
            dto.getId(),
            dto.getUsername(),
            passwordEncoder.encode(dto.getPassword()),
            dto.getEmail(),
            rol,
            true,
            null
        );
    }
}
