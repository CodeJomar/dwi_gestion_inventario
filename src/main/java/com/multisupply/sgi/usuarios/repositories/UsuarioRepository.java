package com.multisupply.sgi.usuarios.repositories;

import com.multisupply.sgi.usuarios.entities.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    
    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre <> 'ROLE_ADMINISTRADOR'")
    List<Usuario> findAllExceptAdmin();
}

