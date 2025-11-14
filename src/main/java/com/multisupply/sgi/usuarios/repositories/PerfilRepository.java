package com.multisupply.sgi.usuarios.repositories;

import com.multisupply.sgi.usuarios.entities.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {
    Optional<Perfil> findByUsuarioId(String usuarioId);
}
