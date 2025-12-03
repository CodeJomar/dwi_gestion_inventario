package com.multisupply.sgi.ordenes.repositories;

import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.entities.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, String>, JpaSpecificationExecutor<Orden> {
    
    long countByTipo(TipoOrden tipo);
    
    @Query("SELECT COALESCE(SUM(o.monto), 0) FROM Orden o WHERE o.tipo = :tipo")
    BigDecimal totalMontoByTipo(@Param("tipo") TipoOrden tipo);
    
    List<Orden> findByFechaEliminacionIsNullOrderByFechaCreacionDesc();
    
    @Query("""
        SELECT SUM(o.monto)
        FROM Orden o
        WHERE o.tipo = 'SALIDA'
        AND o.fechaEliminacion IS NULL
        AND YEAR(o.fechaCreacion) = :year
    """)
    BigDecimal sumMontoSalidaByYear(@Param("year") int year);
    
    
    @Query("""
        SELECT SUM(o.monto)
        FROM Orden o
        WHERE o.tipo = 'SALIDA'
        AND o.fechaEliminacion IS NULL
        AND YEAR(o.fechaCreacion) = :year
        AND MONTH(o.fechaCreacion) = :month
    """)
    BigDecimal sumMontoSalidaByMonth(@Param("year") int year,
                                     @Param("month") int month);
}