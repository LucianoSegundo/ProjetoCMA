package com.ifpe.projetoCMA.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ifpe.projetoCMA.controller.dto.CadastroRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message ="Nome nao deve ser nulo")
	@Column(unique = true)
	private String nome;
	
	@NotBlank(message ="Email nao deve ser nulo")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message ="Senha nao deve ser nula")
	private String senha;
	
	@ManyToMany(mappedBy = "usuarios")
	Set<Papel> Papeis;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Notificacao> notificacao;

	public Usuario() {
		super();
	}
	
	public Usuario(CadastroRequest usuario) {
		this.nome = usuario.nome();
		this.email = usuario.email();
		this.senha = usuario.senha();
		this.Papeis = new HashSet<Papel>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Boolean  addPapel(Papel papel) {
		return this.Papeis.add(papel);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Papel> getPapeis() {
		return Papeis;
	}

	public void setPapeis(Set<Papel> papeis) {
		Papeis = papeis;
	}

	public List<Notificacao> getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(List<Notificacao> notificacao) {
		this.notificacao = notificacao;
	}

	
	
}
