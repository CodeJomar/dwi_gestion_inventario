package com.multisupply.sgi.productos.entities.dtos;

import com.multisupply.sgi.productos.entities.enums.CategoriaProducto;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    
    private String id;
    
    private String nombre;
    
    private int stock;
    
    private String imagen;
    
    private BigDecimal precio;
    
    private String descripcion;
    
    private CategoriaProducto categoria;
    
    private EstadoProducto estado;
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaActualizacion;
    
    private String creadoPor;

    private String actualizadoPor;
}

