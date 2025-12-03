package com.multisupply.sgi.productos.repositories;

import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.productos.entities.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String>, JpaSpecificationExecutor<Producto> {

    Optional<Producto> findByNombre(String nombre);
    
    List<Producto> findAllByEstado(EstadoProducto estado);
    
    long countByEstado(EstadoProducto estado);
    
    long countByStockLessThan(int cantidad);
    
}