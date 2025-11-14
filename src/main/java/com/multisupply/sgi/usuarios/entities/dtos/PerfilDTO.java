package com.multisupply.sgi.usuarios.entities.dtos;

import com.multisupply.sgi.usuarios.entities.enums.Pais;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    
    private String id;
    
    @NotBlank(message = "El nombre completo es obligatorio.")
    private String nombreCompleto;
    
    @NotBlank(message = "El DNI es obligatorio.")
    @Digits(integer = 8, fraction = 0, message = "El DNI solo debe contener hasta 8 dígitos (solo números).")
    @Size(min = 8, max = 8, message = "El DNI debe contener 8 caracteres.")
    private String dni;
    
    @NotBlank(message = "El teléfono es obligatorio.")
    @Digits(integer = 9, fraction = 0, message = "El teléfono solo debe contener hasta 9 dígitos (solo números).")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos.")
    private String telefono;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message = "La fecha no puede ni presente o futura.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    
    private int edad;
    
    @NotNull(message = "El país es obligatorio.")
    private Pais pais;
    
    // Datos del usuario dueño del perfil
    private String usuarioId;
    
    private String username;
    
    private String email;

}
