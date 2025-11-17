package com.multisupply.sgi.ordenes.repositories;

import com.multisupply.sgi.ordenes.entities.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, String>, JpaSpecificationExecutor<Orden> {
}