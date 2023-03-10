package com.egg.biblioteca.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entidades.Usuario;

@Repository //  @Repository = Indica que es una clase repositorio
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
  // JpaRepository<Usuario, String> = Extiende de repositorio JPA y maneja entidad Usuario, id = String>

  @Query("SELECT u FROM Usuario u WHERE u.email = :email")
  public Usuario buscarPorEmail(@Param("email") String email);
}