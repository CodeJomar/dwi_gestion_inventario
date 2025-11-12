package com.multisupply.sgi.productos.repositories;

import com.multisupply.sgi.productos.entities.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    Optional<Producto> findByNombre(String nombre);
}