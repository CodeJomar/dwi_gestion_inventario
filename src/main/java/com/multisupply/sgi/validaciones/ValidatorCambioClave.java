package com.multisupply.sgi.validaciones;

import com.multisupply.sgi.usuarios.entities.dtos.CambioPasswordDTO;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import com.multisupply.sgi.validaciones.anotaciones.CambioClave;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidatorCambioClave implements ConstraintValidator<CambioClave, CambioPasswordDTO> {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public boolean isValid(CambioPasswordDTO dto, ConstraintValidatorContext context) {
        
        if (dto == null || dto.getEmail() == null) return true;
        
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
        
        if (optUsuario.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("No existe ningún usuario con este correo.")
                .addPropertyNode("email")
                .addConstraintViolation();
            return false;
        }
        
        Usuario usuario = optUsuario.get();
        
        if (!passwordEncoder.matches(dto.getOldPassword(), usuario.getPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La contraseña actual no es correcta.")
                .addPropertyNode("oldPassword")
                .addConstraintViolation();
            return false;
        }
        
        if (passwordEncoder.matches(dto.getNewPassword(), usuario.getPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La nueva contraseña no puede ser igual a la actual.")
                .addPropertyNode("newPassword")
                .addConstraintViolation();
            return false;
        }
        
        return true;
    }
}