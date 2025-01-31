package com.ifpe.projetoCMA.exception;

public class verificacaoCamposNulosException extends RuntimeException {

	public verificacaoCamposNulosException(String mensagem) {
        super("durante a verificação do objeto da classe "+ mensagem + "algo deu errado");  // Passa a mensagem para o construtor da classe Exception
    }
}
