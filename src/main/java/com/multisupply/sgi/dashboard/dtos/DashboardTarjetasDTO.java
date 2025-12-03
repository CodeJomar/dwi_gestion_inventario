package com.multisupply.sgi.dashboard.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardTarjetasDTO {

    private long totalProductosActivos;
    private String ingresosAnuales;   // Ej: "S/ 12,300.00"
    private String ingresosMensuales; // Ej: "S/ 3,800.00"
    private long productosStockBajo;
}