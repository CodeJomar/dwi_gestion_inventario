package com.multisupply.sgi.usuarios.entities.models;

import com.multisupply.sgi.usuarios.entities.enums.Pais;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "perfiles_usuarios")
public class Perfil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "nombre_completo", length = 255)
    private String nombreCompleto;
    
    @Column(name = "documento_identidad", length = 8)
    private String dni;
    
    @Column(name = "telefono", length = 9)
    private String telefono;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "edad")
    private int edad;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pais")
    private Pais paises;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario; // Aca debe tener el email y username del usuario
    
}
