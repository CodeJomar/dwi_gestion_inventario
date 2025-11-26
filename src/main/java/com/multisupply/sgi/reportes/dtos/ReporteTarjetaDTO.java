package com.multisupply.sgi.reportes.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReporteTarjetaDTO {
    
    private long productosActivos;
    private long ordenesEntrada;
    private long ordenesSalida;
    private BigDecimal ganancias;

}
