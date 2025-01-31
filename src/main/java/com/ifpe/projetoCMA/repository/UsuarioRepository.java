package com.ifpe.projetoCMA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifpe.projetoCMA.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Boolean existsByNome(String nome);
	public Boolean existsByEmail(String nome);

	
	Optional<Usuario> findByNome(String nome);
	
}
