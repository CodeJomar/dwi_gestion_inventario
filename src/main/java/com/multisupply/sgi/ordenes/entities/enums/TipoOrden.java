package com.multisupply.sgi.ordenes.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoOrden {
    
    ENTRADA("Entrada", ""),
    SALIDA("Salida", "");
    
    private final String displayName;
    private final String cssClass;
    
}
