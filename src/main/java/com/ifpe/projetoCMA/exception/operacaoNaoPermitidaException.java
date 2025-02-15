package com.ifpe.projetoCMA.exception;

public class operacaoNaoPermitidaException extends RuntimeException {

	operacaoNaoPermitidaException(){
		super();
	}
	
	operacaoNaoPermitidaException(String mensagem){
		super(mensagem);
	}
}
