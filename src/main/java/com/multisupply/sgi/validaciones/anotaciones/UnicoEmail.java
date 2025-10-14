package com.multisupply.sgi.validaciones.anotaciones;

import com.multisupply.sgi.validaciones.ValidatorEmail;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatorEmail.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UnicoEmail {
    String message() default "Ya existe un usuario que ha registrado este correo...";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
