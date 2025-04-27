package com.ifpe.projetoCMA.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
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
	
	@OneToMany(mappedBy = "turma", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Postagem> Postagens;
	
	public Turma() {
		this.coreografias = new ArrayList<Coreografia>();
		this.Alunos = new ArrayList<Usuario>();
		this.Postagens = new ArrayList<Postagem>();

	}
	
	public Turma(Usuario professor) {
		this.coreografias = new ArrayList<Coreografia>();
		this.Alunos = new ArrayList<Usuario>();
		this.professor = professor;
		this.Postagens = new ArrayList<Postagem>();

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

	@Override
	public int hashCode() {
		return Objects.hash(Alunos, coreografias, id, nome, professor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		return Objects.equals(Alunos, other.Alunos) && Objects.equals(coreografias, other.coreografias)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(professor, other.professor);
	}

	public List<Postagem> getPostagens() {
		return Postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		Postagens = postagens;
	}
	
	public Boolean  addPostagem(Postagem postagens) {
		return this.Postagens.add(postagens);
	}
	
	
	
}
