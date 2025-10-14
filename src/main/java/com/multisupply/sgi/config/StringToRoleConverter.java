package com.multisupply.sgi.config;

import com.multisupply.sgi.usuarios.entities.models.Rol;
import com.multisupply.sgi.usuarios.repositories.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToRoleConverter implements Converter<String, Rol> {
    
    private final RolRepository rolRepo;
    
    @Override
    public Rol convert(String id){
        return rolRepo.findById(Long.valueOf(id)).orElse(null);
    }
}

