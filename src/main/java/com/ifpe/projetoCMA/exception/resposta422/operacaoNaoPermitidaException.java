package com.ifpe.projetoCMA.exception.resposta422;

public class operacaoNaoPermitidaException extends RuntimeException {

	public operacaoNaoPermitidaException(){
		super();
	}
	
	public operacaoNaoPermitidaException(String mensagem){
		super(mensagem);
	}
}
