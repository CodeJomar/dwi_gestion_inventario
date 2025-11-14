package com.multisupply.sgi.usuarios.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Pais {
    
    PERU("Perú"),
    ARGENTINA("Argentina"),
    BOLIVIA("Bolivia"),
    BRASIL("Brasil"),
    COLOMBIA("Colombia"),
    ECUADOR("Ecuador"),
    CHILE("Chile"),
    PARAGUAY("Paraguay"),
    URUGUAY("Uruguay"),
    VENEZUELA("Venezuela");
    
    private final String displayName;
}
