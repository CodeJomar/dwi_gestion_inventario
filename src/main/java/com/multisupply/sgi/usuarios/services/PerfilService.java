package com.multisupply.sgi.usuarios.services;

import com.multisupply.sgi.usuarios.entities.dtos.PerfilDTO;
import com.multisupply.sgi.usuarios.entities.models.Perfil;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.PerfilRepository;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilService {
    
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    
    public Optional<PerfilDTO> obtenerPerfilPorUsuarioId(String usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId)
            .map(this::mapToDTO);
    }
    
    @Transactional
    public PerfilDTO guardarPerfil(PerfilDTO dto) {
        
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        
        Perfil perfil = (dto.getId() == null)
            ? new Perfil()
            : perfilRepository.findById(dto.getId()).orElse(new Perfil());
        
        perfil.setUsuario(usuario);
        perfil.setNombreCompleto(dto.getNombreCompleto());
        perfil.setDni(dto.getDni());
        perfil.setTelefono(dto.getTelefono());
        perfil.setFechaNacimiento(dto.getFechaNacimiento());
        perfil.setEdad(calcularEdad(dto.getFechaNacimiento()));
        perfil.setPaises(dto.getPais());
        
        Perfil guardado = perfilRepository.save(perfil);
        
        return mapToDTO(guardado);
    }
    
    private PerfilDTO mapToDTO(Perfil perfil) {
        
        PerfilDTO dto = new PerfilDTO();
        
        dto.setId(perfil.getId());
        dto.setNombreCompleto(perfil.getNombreCompleto());
        dto.setDni(perfil.getDni());
        dto.setTelefono(perfil.getTelefono());
        dto.setFechaNacimiento(perfil.getFechaNacimiento());
        dto.setEdad(perfil.getEdad());
        dto.setPais(perfil.getPaises());
        
        Usuario u = perfil.getUsuario();
        dto.setUsuarioId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        
        return dto;
    }
    
    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}

