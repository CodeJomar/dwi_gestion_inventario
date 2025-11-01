package com.multisupply.sgi.ordenes.entities.dtos;

import com.multisupply.sgi.ordenes.entities.enums.MotivoOrden;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
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
    
    private Integer cantidad;
    
    private BigDecimal monto;
    
    private MotivoOrden motivo;
    
    private TipoOrden tipo;
    
    private String productoId;
    
    private String productoNombre;
    
    private LocalDateTime fechaCreacion;
    private String creadoPor;
    
    private LocalDateTime fechaActualizacion;
    private String actualizadoPor;
    
    private LocalDateTime fechaEliminacion;
    private String eliminadoPor;
}

