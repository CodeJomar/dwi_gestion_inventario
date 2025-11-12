package com.multisupply.sgi.ordenes.entities.dtos;

import com.multisupply.sgi.ordenes.entities.enums.MotivoOrden;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
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
public class OrdenDTO {

    private String id;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private Integer cantidad;

    private BigDecimal monto;

    @NotNull(message = "Debe seleccionar un motivo.")
    private MotivoOrden motivo;

    @NotNull(message = "Debe seleccionar un tipo de orden.")
    private TipoOrden tipo;

    @NotBlank(message = "Debe seleccionar un producto.")
    private String productoId;

    private String productoNombre;
    private LocalDateTime fechaCreacion;
    private String creadoPor;
    private LocalDateTime fechaActualizacion;
    private String actualizadoPor;
    private LocalDateTime fechaEliminacion;
    private String eliminadoPor;
}