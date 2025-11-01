package com.multisupply.sgi.productos.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoriaProducto {
    CELULARES("Celulares", ""),
    COMPUTO("Computo", ""),
    AUDIO("Audio", ""),
    TV("TV", ""),
    GAMER("Gamer", ""),
    CAMARA("Cámara", ""),
    ACCESORIOS("Accesorios", "");
    
    private final String displayName;
    private final String cssClass;
}
