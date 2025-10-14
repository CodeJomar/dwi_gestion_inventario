package com.multisupply.sgi.usuarios.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum RolList {
    ROLE_ADMINISTRADOR("Administrador", ""),
    ROLE_EMPLEADO("Empleado", "");
    
    private final String displayName;
    private final String cssClass;
}
