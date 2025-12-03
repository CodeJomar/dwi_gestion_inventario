package com.multisupply.sgi.dashboard.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardBarrasDTO {

    private String id;
    private String nombre;

    private int stockActual;
    private int stockMaximo;      // un número que defines o inferimos (por ejemplo 100 o stock histórico)

    private int porcentaje;       // % del stock actual respecto al máximo
    private String nivel;         // "Crítico", "Bajo", "Moderado"
    private String cssClass;      // badge o color opcional ("badge-destructive")
}