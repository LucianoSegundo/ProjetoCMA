package com.ifpe.projetoCMA.entity;

import java.util.ArrayList;
import java.util.List;

import com.ifpe.projetoCMA.controller.dto.response.AtividadeResponse;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"coreo_id", "nome"}))
public class Atividades {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column()
	private String nome;

	@ElementCollection
	private List<String> conteudo;
	
	@ManyToOne
	@JoinColumn(name = "coreo_id")
	private Coreografia coreografia;

	public Atividades() {
		super();
		this.conteudo = new ArrayList<>();

	}
	
	public Atividades(Coreografia coreografia) {
		super();
		this.coreografia = coreografia;
		this.conteudo = new ArrayList<>();
	}
	
	public boolean adicionarConteudo(String conteudo) {
		if(conteudo ==null || conteudo.isBlank()) return false;
		this.conteudo.add(conteudo);
		return true;

	}
	
	public boolean removerConteudo(String conteudo) {
		if(conteudo ==null || conteudo.isBlank()) return false;
		this.conteudo.remove(conteudo);
		return true;
		}

	public AtividadeResponse toResponse() {
		
		return new AtividadeResponse(id, nome, conteudo);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getConteudo() {
		return conteudo;
	}

	public void setConteudo(List<String> conteudo) {
		this.conteudo = conteudo;
	}

	public Coreografia getCoreografia() {
		return coreografia;
	}

	public void setCoreografia(Coreografia coreografia) {
		this.coreografia = coreografia;
	}
	
	
}
