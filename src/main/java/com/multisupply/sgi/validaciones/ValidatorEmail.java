package com.multisupply.sgi.validaciones;

import com.multisupply.sgi.usuarios.entities.dtos.UsuarioDTO;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import com.multisupply.sgi.validaciones.anotaciones.UnicoEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidatorEmail implements ConstraintValidator<UnicoEmail, UsuarioDTO> {
    
    @Autowired
    private UsuarioRepository userRepository;
    
    @Override
    public boolean isValid(UsuarioDTO usuario, ConstraintValidatorContext context) {
        if (usuario == null || usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return true;
        }
        
        String username = usuario.getEmail();
        String userId = usuario.getId();
        
        Optional<Usuario> usuarioExistente = userRepository.findByEmail(username);
        
        if (userRepository == null) {
            return true;
        }
        
        if (usuarioExistente.isPresent()) {
            boolean esMismoUsuario = userId != null && usuarioExistente.get().getId().equals(userId);
            if (!esMismoUsuario) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context
                        .getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email")
                    .addConstraintViolation();
            }
            return esMismoUsuario;
        }
        return true;
    }
}
