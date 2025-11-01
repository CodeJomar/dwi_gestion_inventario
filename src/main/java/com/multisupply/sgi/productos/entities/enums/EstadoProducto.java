package com.multisupply.sgi.productos.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EstadoProducto {
    
    ACTIVO("Activo", ""),
    INACTIVO("Inactivo", "");
    
    private final String displayName;
    private final String cssClass;
    
}
