package com.ifpe.projetoCMA.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Papel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String autoridade;
	
	@ManyToMany
	@JoinTable(
	name = "usuario_papel",
	joinColumns = @JoinColumn(name = "papel_id"),
	inverseJoinColumns = @JoinColumn(name = "usuario_id")
	)
	Set<Usuario> usuarios;
	
	public Papel() {
		super();
	}

	public Papel( String autoridade, Set<Usuario> usuario) {
		super();
		this.autoridade = autoridade;
		this.usuarios = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuario(Set<Usuario> usuario) {
		this.usuarios = usuario;
	}

	public String getAutoridade() {
		return autoridade;
	}

	public void setAutoridade(String autoridade) {
		this.autoridade = autoridade;
	}

	
	
	
	
}
