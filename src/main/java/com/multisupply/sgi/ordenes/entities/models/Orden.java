package com.multisupply.sgi.ordenes.entities.models;

import com.multisupply.sgi.ordenes.entities.enums.MotivoOrden;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.productos.entities.models.Producto;
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
@Table(name = "ordenes")
public class Orden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private int cantidad;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MotivoOrden motivo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoOrden tipo;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    private Usuario creadoPor;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @ManyToOne
    @JoinColumn(name = "actualizado_por")
    private Usuario actualizadoPor;
    
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;
    
    @ManyToOne
    @JoinColumn(name = "eliminado_por")
    private Usuario eliminadoPor;
    
}
