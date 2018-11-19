package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByEmail(String email);

}
