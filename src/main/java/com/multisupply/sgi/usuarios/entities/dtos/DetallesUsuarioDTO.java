package com.multisupply.sgi.usuarios.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallesUsuarioDTO {
    
    // Datos del Usuario
    private String idUsuario;
    private String username;
    private String email;
    private String rol;
    private boolean activo;
    private String fechaCreacion;
    
    // Datos del Perfil
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private Integer edad;
    private String fechaNacimiento;
    private String pais;
}
