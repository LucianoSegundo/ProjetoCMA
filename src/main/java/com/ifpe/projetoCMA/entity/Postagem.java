package com.ifpe.projetoCMA.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ifpe.projetoCMA.controller.dto.response.PostagemResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String conteudo;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	
	@OneToMany(mappedBy = "postagempai", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comentario> comentarios;
	
	private OffsetDateTime datapublicacao;

	public Postagem() {
		this.comentarios = new ArrayList<Comentario>();
		this.setDatapublicacao(OffsetDateTime.now());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public OffsetDateTime getDatapublicacao() {
		return datapublicacao;
	}

	public void setDatapublicacao(OffsetDateTime datapublicacao) {
		this.datapublicacao = datapublicacao;
	}
	
	public PostagemResponse toResponse() {
		
		String data = datapublicacao.getDayOfMonth() + "/";
		data += datapublicacao.getMonth() + "/";
		data += datapublicacao.getYear();
		
		String hora = datapublicacao.getHour() + ":";
		hora += datapublicacao.getMinute(); 

		return new PostagemResponse(titulo, conteudo, autor.getNome(), data, hora);
		
	}
}
