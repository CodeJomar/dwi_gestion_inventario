package com.multisupply.sgi.usuarios.entities.dtos;

import com.multisupply.sgi.validaciones.anotaciones.FormatoClave;
import com.multisupply.sgi.validaciones.anotaciones.UnicoEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UnicoEmail
public class UsuarioDTO {
    
    private String id;
    
    @NotBlank(message = "El nombre de usuario no puede estar vacío...")
    private String username;
    
    @NotBlank(message = "La contraseña no puede estar vacía...")
    @FormatoClave
    private String password;
    
    @NotBlank(message = "El correo electrónico no puede estar vacío...")
    @Email(message = "El correo electrónico ingresado no es válido...")
    private String email;
    
}
