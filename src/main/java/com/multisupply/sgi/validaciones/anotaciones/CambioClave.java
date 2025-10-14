package com.multisupply.sgi.validaciones.anotaciones;

import com.multisupply.sgi.validaciones.ValidatorCambioClave;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = ValidatorCambioClave.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CambioClave {
    String message() default "Error al validar el cambio de contraseña.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
