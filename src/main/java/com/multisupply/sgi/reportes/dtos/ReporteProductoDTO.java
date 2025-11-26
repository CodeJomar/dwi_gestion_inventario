package com.multisupply.sgi.reportes.dtos;

import com.multisupply.sgi.productos.entities.enums.CategoriaProducto;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteProductoDTO {
    
    private String id;
    private String nombre;
    private int stock;
    private BigDecimal precio;
    private CategoriaProducto categoria;
    private EstadoProducto estado;
    private String fechaCreacion;
    private Usuario creadoPor;
    private String fechaActualizacion;
    private Usuario actualizadoPor;
    
}
