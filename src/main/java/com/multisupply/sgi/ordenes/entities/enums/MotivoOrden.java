package com.multisupply.sgi.ordenes.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MotivoOrden {
    
    REPOSICION("Reposición", ""),
    AJUSTE("Ajuste", ""),
    DEVOLUCION("Devolución", ""),
    VENTA("Venta", "");
    
    private final String displayName;
    private final String cssClass;
    
}
