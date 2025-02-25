package com.ifpe.projetoCMA.entity;

import com.ifpe.projetoCMA.controller.dto.response.QuestionarioResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_questionario")
public class Questionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(mappedBy = "questionario", cascade = CascadeType.ALL,  orphanRemoval = true )
	private QuestHonney questHonney;
	
	@OneToOne(mappedBy = "questionario", cascade = CascadeType.ALL,  orphanRemoval = true )
	private QuestVack vack;
	
	@OneToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;

	public Questionario() {
		super();
		
	}
	public Questionario(Usuario autor) {
		super();
		this.vack = new QuestVack(this);
		this.questHonney = new QuestHonney(this);
		this.autor = autor;
	}
	
	public QuestionarioResponse toResponse() {
		return new QuestionarioResponse(id, vack.toVackResponse() , questHonney.toHonneyResponse());
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public QuestHonney getQuestHonney() {
		return questHonney;
	}

	public void setQuestHonney(QuestHonney questHonney) {
		this.questHonney = questHonney;
	}

	public QuestVack getVack() {
		return vack;
	}

	public void setVack(QuestVack vack) {
		this.vack = vack;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	
	
}
