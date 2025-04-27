package com.ifpe.projetoCMA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.projetoCMA.entity.Comentario;
import com.ifpe.projetoCMA.service.ComentarioService;
import com.ifpe.projetoCMA.service.ExtratorId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "api/v1/comentar")
public class ComentarioController {

	@Autowired
	private ComentarioService service;
	
	public ComentarioController() {}
	
	
	@Operation(summary = "Rota para comentar em uma postagem", description = "comenta em uma postagem")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "404", description = "Postagem ou autor não encontrado")
	@ApiResponse(responseCode = "422", description = "Comentario não pode estar vazio")
	@PostMapping(value = "/postagem/{idPostagem}")
	ResponseEntity<List<Comentario>> comentarPostagem(JwtAuthenticationToken token, @PathVariable Long idPostagem, @RequestBody String mensagem){
		
		
		List<Comentario> comentarios = service.comentarPostagem(idPostagem,mensagem, ExtratorId.extrair(token) );
		
		return ResponseEntity.ok(comentarios);
	};
	
	@Operation(summary = "Rota para comentar em uma Coreografia", description = "comenta em uma Coreografia")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "404", description = "Coreografia ou autor não encontrado")
	@ApiResponse(responseCode = "422", description = "Comentario não pode estar vazio")
	@PostMapping(value = "/Coreografia/{idCoreografia}")
	ResponseEntity<List<Comentario>> comentarCoreografia(JwtAuthenticationToken token, @PathVariable Long idCoreografia, @RequestBody String mensagem){
		
		List<Comentario> comentarios = service.comentarCoreografia(idCoreografia,mensagem, ExtratorId.extrair(token) );
		
		return ResponseEntity.ok(comentarios);
		
	};
	
	@Operation(summary = "Rota para comentar em uma Comentario", description = "comenta em uma Comentario")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "404", description = "Comentario pai ou autor não encontrado")
	@ApiResponse(responseCode = "422", description = "Comentario não pode estar vazio")
	@PostMapping(value = "/Comentario/{idcomentario}")
	public ResponseEntity<List<Comentario>> ResponderComentario(JwtAuthenticationToken token, @PathVariable Long idcomentario, @RequestBody String mensagem){
		
		List<Comentario> comentarios = service.ResponderComentario(idcomentario,mensagem, ExtratorId.extrair(token) );
		
		return ResponseEntity.ok(comentarios);
	};
	
	@Operation(summary = "Rota para consultar comentarios em postagens", description = "retorna uma lista de comentarios em uma postagem")
	@ApiResponse(responseCode = "200", description = "consulta bem sucedido")
	@GetMapping(value = "/postagem/{idPostagem}")
	public ResponseEntity<List<Comentario>> ConsultarComentarioPostagem(JwtAuthenticationToken token, @PathVariable Long idPostagem){

		List<Comentario> comentarios = service.ConsultarComentarioPostagem(idPostagem);
		
		return ResponseEntity.ok(comentarios);

	}
	
	@Operation(summary = "Rota para consultar comentarios em um comentario", description = "retorna uma lista de comentarios em uma comentario")
	@ApiResponse(responseCode = "200", description = "consulta bem sucedido")
	@GetMapping(value = "/Comentario/{idcomentario}")
	public ResponseEntity<List<Comentario>> ConsultarComentarioComentario(JwtAuthenticationToken token, @PathVariable  Long idcomentario){

		List<Comentario> comentarios = service.ConsultarComentarioComentario(idcomentario);
		
		return ResponseEntity.ok(comentarios);

	}
	
	@GetMapping(value = "/Coreografia/{idCoreografia}")
	public ResponseEntity<List<Comentario>> ConsultarComentarioCoreografia(JwtAuthenticationToken token, @PathVariable  Long idCoreografia){

		List<Comentario> comentarios = service.ConsultarComentarioCoreografia(idCoreografia);
		
		return ResponseEntity.ok(comentarios);

	}
	
}
