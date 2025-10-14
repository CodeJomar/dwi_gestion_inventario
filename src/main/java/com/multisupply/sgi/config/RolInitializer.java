package com.multisupply.sgi.config;

import com.multisupply.sgi.usuarios.entities.enums.RolList;
import com.multisupply.sgi.usuarios.entities.models.Rol;
import com.multisupply.sgi.usuarios.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class RolInitializer {
    
    @Bean
    public CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            for (RolList rolName : RolList.values()) {
                rolRepository.findByNombre(rolName)
                    .or(() -> {
                        //System.out.println("🧱 Creando rol: " + rolName);
                        return Optional.of(rolRepository.save(new Rol(null, rolName)));
                    });
            }
        };
    }
}
