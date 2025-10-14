package com.multisupply.sgi.validaciones;

import com.multisupply.sgi.validaciones.anotaciones.FormatoClave;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorClave implements ConstraintValidator<FormatoClave, String> {
    
    private static final String REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?{}\\[\\]-]).{6,}$";
    
    @Override
    public boolean isValid(String clave, ConstraintValidatorContext context) {
        if (clave == null || clave.isBlank()) return true;
        return clave.matches(REGEX);
    }
}