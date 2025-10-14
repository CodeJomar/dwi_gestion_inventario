package com.multisupply.sgi.usuarios.entities.dtos;

import com.multisupply.sgi.validaciones.anotaciones.CambioClave;
import com.multisupply.sgi.validaciones.anotaciones.FormatoClave;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CambioClave
public class CambioPasswordDTO {
    
    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ingresar un correo válido.")
    private String email;
    
    @NotBlank(message = "Debe ingresar su contraseña actual.")
    private String oldPassword;
    
    @NotBlank(message = "Debe ingresar la nueva contraseña.")
    @FormatoClave
    private String newPassword;
}
