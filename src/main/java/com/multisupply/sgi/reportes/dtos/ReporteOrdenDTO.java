package com.multisupply.sgi.reportes.dtos;

import com.multisupply.sgi.ordenes.entities.enums.MotivoOrden;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteOrdenDTO {
    
    private String id;
    private String productoNombre;
    private int cantidad;
    private BigDecimal monto;
    private MotivoOrden motivo;
    private TipoOrden tipo;
    private String fechaCreacion;
    private Usuario creadoPor;
    private String fechaActualizacion;
    private Usuario actualizadoPor;

}
