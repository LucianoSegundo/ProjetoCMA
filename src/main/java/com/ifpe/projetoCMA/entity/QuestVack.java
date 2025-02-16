package com.ifpe.projetoCMA.entity;

import com.ifpe.projetoCMA.controller.dto.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.VackRequest;
import com.ifpe.projetoCMA.controller.dto.VackResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_questvack")
public class QuestVack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int visuak;
	private int auditivo;
	private int cinestesico;
	private int leituraEscrita;
	
	@OneToOne
	@JoinColumn(name = "questionario_id")
	private Questionario  questionario;
	
	public QuestVack() {
		super();
	}
	
	public QuestVack(VackRequest vack) {
	this.preecher(vack);
	}
	
	public void preecher(VackRequest vack) {
		this.auditivo = vack.auditivo();
		this.cinestesico = vack.cinestesico();
		this.visuak = vack.visuak();
		this.leituraEscrita = vack.leituraEscrita();
	}
	
	public VackResponse toVackResponse() {
		VackResponse response = new VackResponse(
				this.id,
				this.visuak,
				this.auditivo,
				this.cinestesico,
				this.leituraEscrita,
				retornarEstilo());
		
		return response;
	}
	
	
public String retornarEstilo() {
		if(this.auditivo ==0 && this.auditivo ==0 && this.cinestesico ==0 && this.leituraEscrita == 0)return "Sem estilo definido";
		
		return "Ainda não implementado"; 
	}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public int getVisuak() {
	return visuak;
}

public void setVisuak(int visuak) {
	this.visuak = visuak;
}

public int getAuditivo() {
	return auditivo;
}

public void setAuditivo(int auditivo) {
	this.auditivo = auditivo;
}

public int getCinestesico() {
	return cinestesico;
}

public void setCinestesico(int cinestesico) {
	this.cinestesico = cinestesico;
}

public int getLeituraEscrita() {
	return leituraEscrita;
}

public void setLeituraEscrita(int leituraEscrita) {
	this.leituraEscrita = leituraEscrita;
}

public Questionario getQuestionario() {
	return questionario;
}

public void setQuestionario(Questionario questionario) {
	this.questionario = questionario;
}

public void serar() {
	this.auditivo = 0;
	this.cinestesico = 0;
	this.visuak = 0;
	this.leituraEscrita = 0;
	
}
}
