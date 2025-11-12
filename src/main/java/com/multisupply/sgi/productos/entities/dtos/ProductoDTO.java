package com.multisupply.sgi.productos.entities.dtos;

import com.multisupply.sgi.productos.entities.enums.CategoriaProducto;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser 0 o negativo")
    private int stock;

    private String imagen;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    private String descripcion;

    @NotNull(message = "Debe seleccionar una categoría")
    private CategoriaProducto categoria;

    @NotNull(message = "Debe seleccionar un estado")
    private EstadoProducto estado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String creadoPor;
    private String actualizadoPor;
}