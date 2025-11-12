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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    
    // Crear Usuario
    public UsuarioDTO crearUsuario(UsuarioDTO dto, RolList rolNombre) {
        Rol rol = rolRepository.findByNombre(rolNombre)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado en la base de datos."));
        
        Usuario usuario = mapToEntity(dto, rol);
        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }
    
    // Leer usuarios activos (excepto administradores)
    public List<UsuarioDTO> listarUsuariosActivos() {
        return usuarioRepository.findAll().stream()
            .filter(Usuario::isActivo)
            .filter(u -> u.getRol().getNombre() != RolList.ROLE_ADMINISTRADOR)
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
    
    // Leer todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    // Ver detalles de un usuario (solo lectura)
    public Optional<UsuarioDTO> obtenerUsuario(String id) {
        return usuarioRepository.findById(id)
            .filter(u -> u.getRol().getNombre() != RolList.ROLE_ADMINISTRADOR)
            .map(this::mapToDTO);
    }
    
    // Desactivar usuario (soft delete)
    public boolean desactivarUsuario(String id) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setActivo(false);
                usuarioRepository.save(usuario);
                return true;
            })
            .orElse(false);
    }
    
    // Autenticación (solo usuarios activos)
    public Optional<UsuarioDTO> autenticar(String email, String password) {
        return usuarioRepository.findByEmail(email)
            .filter(Usuario::isActivo)
            .filter(u -> passwordEncoder.matches(password, u.getPassword()))
            .map(this::mapToDTO);
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
