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
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String mensagem;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	
	@ManyToOne
	@JoinColumn(name = "coreografia_id")
	private Coreografia coreografia;
	
	@ManyToOne
	@JoinColumn(name = "comentarioPai_id")
	private Comentario comentariopai;
	
	@OneToMany(mappedBy = "comentariopai", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comentario> comentariosfilhos;
	
	@ManyToOne
	@JoinColumn(name = "postagem_id")
	private Postagem postagempai;

	
	
	public Comentario() {
		this.comentariosfilhos = new ArrayList<Comentario>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Coreografia getCoreografia() {
		return coreografia;
	}

	public void setCoreografia(Coreografia coreografia) {
		this.coreografia = coreografia;
	}

	public Comentario getComentariopai() {
		return comentariopai;
	}

	public void setComentariopai(Comentario comentarioPai) {
		this.comentariopai = comentarioPai;
	}

	public List<Comentario> getComentariosfilhos() {
		return comentariosfilhos;
	}

	public void setComentariosfilhos(List<Comentario> comentariosFilhos) {
		this.comentariosfilhos = comentariosFilhos;
	}

	public Postagem getPostagempai() {
		return postagempai;
	}

	public void setPostagempai(Postagem postagemPai) {
		this.postagempai = postagemPai;
	}
	
	
}
