package com.multisupply.sgi.usuarios.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum RolList {
    ROLE_ADMINISTRADOR("Administrador", "badge badge-default"),
    ROLE_EMPLEADO("Empleado", "badge badge-warning");
    
    private final String displayName;
    private final String cssClass;
}
