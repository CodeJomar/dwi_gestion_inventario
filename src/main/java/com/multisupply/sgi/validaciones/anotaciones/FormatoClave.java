package com.multisupply.sgi.validaciones.anotaciones;

import com.multisupply.sgi.validaciones.ValidatorClave;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatorClave.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatoClave {
    
    String message() default "La contraseña debe contener: 6 caracteres, una mayúscula, un número y un carácter especial";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
