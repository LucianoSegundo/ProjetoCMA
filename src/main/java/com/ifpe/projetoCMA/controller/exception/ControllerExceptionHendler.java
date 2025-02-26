package com.ifpe.projetoCMA.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ifpe.projetoCMA.exception.AcessoNegadoException;
import com.ifpe.projetoCMA.exception.CadastroNedadoException;
import com.ifpe.projetoCMA.exception.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.ausenciaDeDadosException;
import com.ifpe.projetoCMA.exception.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.exception.recursoJaExisteException;
import com.ifpe.projetoCMA.exception.verificacaoCamposNulosException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHendler {

	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<ErroPadrao> acessoNegado(AcessoNegadoException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(EntidadeNaoEncontradaException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}
	
	@ExceptionHandler(CadastroNedadoException.class)
	public ResponseEntity<ErroPadrao> cadastroNegado(CadastroNedadoException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}
	
	@ExceptionHandler(operacaoNaoPermitidaException.class)
	public ResponseEntity<ErroPadrao> operacaoNaoPermitida(operacaoNaoPermitidaException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}
	
	@ExceptionHandler(verificacaoCamposNulosException.class)
	public ResponseEntity<ErroPadrao> verificacaoCamposNulos(verificacaoCamposNulosException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}
	
	@ExceptionHandler(ausenciaDeDadosException.class)
	public ResponseEntity<ErroPadrao> dadosEssenciasfaltando(ausenciaDeDadosException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}

	
	@ExceptionHandler(recursoJaExisteException.class)
	public ResponseEntity<ErroPadrao> recurçoExistente(recursoJaExisteException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		ErroPadrao erro =new ErroPadrao(
				Instant.now(),
				status.value(),
				e.getMessage(),
				request.getRequestURI() );
		
		return ResponseEntity.status(status).body(erro);
		
	}
}
	
