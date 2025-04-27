package com.ifpe.projetoCMA.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.ifpe.projetoCMA.controller.dto.request.CadastroRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message ="Usuario nao deve ser nulo")
	@Column(unique = true)
	private String usuario;
	
	@NotBlank(message ="Email nao deve ser nulo")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message ="Nome nao deve ser nula")
	private String nome;
	
	@NotBlank(message ="Senha nao deve ser nula")
	private String senha;
	
	// mapeamento de entidades 
	
	@OneToMany(mappedBy = "professor")
	private List<Turma> turmasCProfessor;
	
	@ManyToMany(mappedBy = "Alunos")
	private List<Turma> turmasCAluno;

	
	@ManyToMany(mappedBy = "usuarios")
	Set<Papel> Papeis;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Notificacao> notificacao;

	@OneToOne(mappedBy = "autor", cascade = CascadeType.ALL,  orphanRemoval = true )
	private Questionario  questionario;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Coreografia> coreografias;
	
	@OneToMany(mappedBy = "autor", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comentario> comentarios;
	
	@OneToMany(mappedBy = "autor", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Postagem> Postagens;
	
	public Usuario() {
		super();
		
		this.Papeis = new HashSet<Papel>();
		this.questionario = new Questionario(this);
		this.notificacao = new ArrayList<Notificacao>();
		this.turmasCAluno = new ArrayList<Turma>();
		this.turmasCProfessor = new  ArrayList<Turma>();
		this.coreografias = new ArrayList<Coreografia>();
		this.Postagens = new ArrayList<Postagem>();
		this.comentarios = new ArrayList<Comentario>();

		
	}
	
	public Usuario(CadastroRequest usuario) {
		this.usuario = usuario.usuario();
		this.nome = usuario.nome();
		this.email = usuario.email();
		this.senha = usuario.senha();
		this.Papeis = new HashSet<Papel>();
		this.questionario = new Questionario(this);
		this.notificacao = new ArrayList<Notificacao>();
		this.turmasCAluno = new ArrayList<Turma>();
		this.turmasCProfessor = new  ArrayList<Turma>();
		this.coreografias = new ArrayList<Coreografia>();
		this.Postagens = new ArrayList<Postagem>();
		this.comentarios = new ArrayList<Comentario>();
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Papeis, coreografias, email, id, nome, notificacao, questionario, senha, turmasCAluno,
				turmasCProfessor, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(Papeis, other.Papeis) && Objects.equals(coreografias, other.coreografias)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(notificacao, other.notificacao)
				&& Objects.equals(questionario, other.questionario) && Objects.equals(senha, other.senha)
				&& Objects.equals(turmasCAluno, other.turmasCAluno)
				&& Objects.equals(turmasCProfessor, other.turmasCProfessor) && Objects.equals(usuario, other.usuario);
	}

	public List<Turma> getTurmasCProfessor() {
		return turmasCProfessor;
	}

	public void setTurmasCProfessor(List<Turma> turmasCProfessor) {
		this.turmasCProfessor = turmasCProfessor;
	}

	public List<Turma> getTurmasCAluno() {
		return turmasCAluno;
	}

	public void setTurmasCAluno(List<Turma> turmasCAluno) {
		this.turmasCAluno = turmasCAluno;
	}

	public List<Coreografia> getCoreografias() {
		return coreografias;
	}

	public void setCoreografias(List<Coreografia> coreografias) {
		this.coreografias = coreografias;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Postagem> getPostagens() {
		return Postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		Postagens = postagens;
	}

	
	
}
