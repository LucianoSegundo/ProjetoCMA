package com.ifpe.projetoCMA.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ifpe.projetoCMA.controller.dto.request.CoreografiaRequest;
import com.ifpe.projetoCMA.controller.dto.response.AtividadeResponse;
import com.ifpe.projetoCMA.controller.dto.response.CoreografiaResponse;
import com.ifpe.projetoCMA.controller.dto.response.QuestionarioResponse;

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
	private Long id;
	
	private String nome;

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
	
	public Coreografia(Usuario usuario, CoreografiaRequest coreo) {
		super();
		this.autor = usuario;
		this.atividades = new ArrayList<Atividades>();
		this.aprendizado = coreo.aprendizado();
		this.conhecimento = coreo.conhecimento();
		this.nome = coreo.nome();
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
	
	public CoreografiaResponse toresponse() {
		
		QuestionarioResponse questResponse = this.autor.getQuestionario().toResponse();
		
		List<AtividadeResponse> atividadesResponse = this.atividades.stream().map(x -> x.toResponse()).collect(Collectors.toList());
		
		CoreografiaResponse response = new CoreografiaResponse(this.id, nome, aprendizado, conhecimento, questResponse , atividadesResponse);
		
		return response;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aprendizado, atividades, autor, conhecimento, id, nome, turma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coreografia other = (Coreografia) obj;
		return Objects.equals(aprendizado, other.aprendizado) && Objects.equals(atividades, other.atividades)
				&& Objects.equals(autor, other.autor) && Objects.equals(conhecimento, other.conhecimento)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(turma, other.turma);
	}
	
	
	
}
