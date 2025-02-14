package com.ifpe.projetoCMA.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_notificacao")
public class Notificacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne()
	private Usuario usuario;
	
	@NotBlank(message ="texto nao deve ser nulo")
	private String texto;
	
	@NotNull(message = "momento não deve ser nulo")
	private Instant momento ;
	
	boolean lido;
	
	@NotBlank(message ="rota nao deve ser nulo")
	private String rota;

	public Notificacao() {
		super();
	}

	public Notificacao(Usuario usuario, String texto, Instant momento,
			boolean lido, String rota) {
		super();
		this.usuario = usuario;
		this.texto = texto;
		this.momento = momento;
		this.lido = lido;
		this.rota = rota;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Instant getMomento() {
		return momento;
	}

	public void setMomento(Instant momento) {
		this.momento = momento;
	}

	public boolean isLido() {
		return lido;
	}

	public void setLido(boolean lido) {
		this.lido = lido;
	}

	public String getRota() {
		return rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}
	
	
}
