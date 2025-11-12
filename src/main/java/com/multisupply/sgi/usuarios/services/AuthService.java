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

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioDTO registrarUsuario(UsuarioDTO dto) {
        Rol rol = rolRepository.findByNombre(RolList.ROLE_ADMINISTRADOR)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado en la base de datos."));
        
        Usuario usuario = mapToEntity(dto, rol);
        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }
    
    @Transactional
    public void actualizarPassword(String email, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
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
