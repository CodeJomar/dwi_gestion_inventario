package com.multisupply.sgi.reportes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsuarioDTO {
    
    private String id;
    private String username;
    private String email;
    private String rol;
    private String activo;
    private String dni;
    private String telefono;
    private String fechaNacimiento;
    private String pais;
    private String fechaCreacion;

}
