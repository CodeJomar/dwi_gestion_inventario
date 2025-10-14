package com.multisupply.sgi.usuarios.entities.models;

import com.multisupply.sgi.usuarios.entities.enums.RolList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "nombre_rol", length = 20)
    private RolList nombre;

}
