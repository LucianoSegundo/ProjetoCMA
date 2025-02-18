package com.ifpe.projetoCMA.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@ManyToOne()
	@JoinColumn(name = "professor_id")
	private Usuario professor;
	
	@ManyToMany
	@JoinTable(
			name = "turma_aluno",
			joinColumns = @JoinColumn(name = "turma_id"),
			inverseJoinColumns = @JoinColumn(name= "aluno_id"))
	private List<Usuario> Alunos;
	
	@OneToMany(mappedBy = "turma")
	private List<Coreografia> coreografias;
	
	public Turma() {
		this.coreografias = new ArrayList<Coreografia>();
		this.Alunos = new ArrayList<Usuario>();
	}
	
	public Turma(Usuario professor) {
		this.coreografias = new ArrayList<Coreografia>();
		this.Alunos = new ArrayList<Usuario>();
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getProfessor() {
		return professor;
	}

	public void setProfessor(Usuario professor) {
		this.professor = professor;
	}

	public List<Usuario> getAlunos() {
		return Alunos;
	}

	public void setAlunos(List<Usuario> alunos) {
		Alunos = alunos;
	}

	public List<Coreografia> getCoreografias() {
		return coreografias;
	}

	public void setCoreografias(List<Coreografia> coreografias) {
		this.coreografias = coreografias;
	}
	
	
}
