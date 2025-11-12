package com.multisupply.sgi.usuarios.services;

import com.multisupply.sgi.usuarios.entities.dtos.UsuarioDTO;
import com.multisupply.sgi.usuarios.entities.enums.RolList;
import com.multisupply.sgi.usuarios.entities.models.Rol;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
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
