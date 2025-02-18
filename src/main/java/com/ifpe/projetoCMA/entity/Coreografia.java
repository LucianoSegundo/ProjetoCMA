package com.ifpe.projetoCMA.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Coreografia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String aprendizado;
	
	private String conhecimento;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	
	@OneToMany(mappedBy = "coreografia", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Atividades> atividades;

	public Coreografia() {
		super();
		this.atividades = new ArrayList<Atividades>();
	}
	
	public Coreografia(Usuario usuario) {
		super();
		this.autor = usuario;
		this.atividades = new ArrayList<Atividades>();
	}
	
	public boolean adicionarAtividade(Atividades atividade) {
		if(atividade ==null) return false;
		this.atividades.add(atividade);
		return true;
	}
	
	public boolean removerAtividade(Atividades atividade) {
		if(atividade ==null) return false;
		this.atividades.remove(atividade);
		return true;
		}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAprendizado() {
		return aprendizado;
	}
	public void setAprendizado(String aprendizado) {
		this.aprendizado = aprendizado;
	}
	public String getConhecimento() {
		return conhecimento;
	}
	public void setConhecimento(String conhecimento) {
		this.conhecimento = conhecimento;
	}
	public Usuario getAutor() {
		return autor;
	}
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	public List<Atividades> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<Atividades> atividades) {
		this.atividades = atividades;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	
	
}
