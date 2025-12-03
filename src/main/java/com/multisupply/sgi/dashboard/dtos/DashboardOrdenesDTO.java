package com.multisupply.sgi.dashboard.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardOrdenesDTO {
    private String id;
    private String productoNombre;
    private String monto;          // Formateado "S/ 1,200.00"
    private String tipo;    // "Entrada" o "Salida"
    private String cssClass;       // badge classname
    private String fecha;          // "01 Feb 2025"
}