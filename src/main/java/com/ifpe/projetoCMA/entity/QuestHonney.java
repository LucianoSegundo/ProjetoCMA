package com.ifpe.projetoCMA.entity;

import com.ifpe.projetoCMA.controller.dto.request.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.response.HonneyResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_questhonney")
public class QuestHonney {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int pragmatico;
	private int teorico;
	private int ativo;
	private int referido;
	
	@OneToOne
	@JoinColumn(name = "questionario_id")
	private Questionario  questionario;
	
	
	public QuestHonney() {
		super();

	}
	
	public QuestHonney(Questionario  questionario) {
		super();
		this.questionario = questionario;

	}
	
	public QuestHonney(HonneyRequest honney,Questionario  questionario) {
		this.preecher(honney);
		this.questionario = questionario;
	}
	
	public void preecher(HonneyRequest honney) {
		this.pragmatico = honney.pragmatico();
		this.teorico = honney.teorico();
		this.ativo = honney.ativo();
		this.referido = honney.referido();

	}

	
	public String retornarEstilo() {
		if(this.ativo ==0 && this.pragmatico ==0 && this.referido ==0 && this.teorico == 0)return "Sem estilo definido";

		
		return "Ainda não implementado"; 
	}

	public HonneyResponse toHonneyResponse() {
		HonneyResponse response = new HonneyResponse(
				this.id,
				this.pragmatico,
				this.teorico,
				this.ativo,
				this.referido,
				retornarEstilo());
		
		return response;
	}
	
	public int getPragmatico() {
		return pragmatico;
	}

	public void setPragmatico(int pragmatico) {
		this.pragmatico = pragmatico;
	}

	public int getTeorico() {
		return teorico;
	}

	public void setTeorico(int teorico) {
		this.teorico = teorico;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public int getReferido() {
		return referido;
	}

	public void setReferido(int referido) {
		this.referido = referido;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public void serar() {
		this.pragmatico =0;
		this.teorico = 0;
		this.ativo = 0;
		this.referido = 0;
		
	}
	
	

	
}
