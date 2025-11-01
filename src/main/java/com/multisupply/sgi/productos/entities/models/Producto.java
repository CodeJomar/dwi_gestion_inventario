package com.multisupply.sgi.productos.entities.models;

import com.multisupply.sgi.productos.entities.enums.CategoriaProducto;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "stock", nullable = false)
    private int stock;
    
    @Column(name = "imagen", nullable = true)
    private String imagen;
    
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;
    
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 20)
    private CategoriaProducto categoria;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoProducto estado;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario creadoPor;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @ManyToOne
    @JoinColumn(name = "actualizado_por")
    private Usuario actualizadoPor;
    
}
