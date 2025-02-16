package com.ifpe.projetoCMA.exception;

public class operacaoNaoPermitidaException extends RuntimeException {

	public operacaoNaoPermitidaException(){
		super();
	}
	
	public operacaoNaoPermitidaException(String mensagem){
		super(mensagem);
	}
}
