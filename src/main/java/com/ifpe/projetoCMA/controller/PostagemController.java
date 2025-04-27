package com.ifpe.projetoCMA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.projetoCMA.controller.dto.request.PostagemRequest;
import com.ifpe.projetoCMA.controller.dto.response.PostagemResponse;
import com.ifpe.projetoCMA.service.ExtratorId;
import com.ifpe.projetoCMA.service.PostagemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "api/v1/postar")
public class PostagemController {
	
	@Autowired
	private PostagemService service;
	
	public PostagemController() {}

	@Operation(summary = "criar postagem em uma turma", description = "cria uma postagem em uma turma")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "404", description = "autor ou turma não encontradas")
	@ApiResponse(responseCode = "422", description = "postagem não pode ter o titulo ou conteudo vazios")
	@PostMapping(value = "/turma/{idTurma}")
	public ResponseEntity<Page<PostagemResponse>> fazerPostagem(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhas", defaultValue = "10") Integer linhas,
			@RequestParam(value = "ordarPor", defaultValue = "datapublicacao") String ordarPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
			JwtAuthenticationToken token, @PathVariable Long idTurma, @RequestBody PostagemRequest postagem){
		
		PageRequest pagi = PageRequest.of(pagina, linhas, Direction.valueOf(ordem), ordarPor);

		Page<PostagemResponse> resultado = service.criarPostagem( postagem, idTurma, pagi, ExtratorId.extrair(token));

		return ResponseEntity.ok(resultado);
	}
	
	@Operation(summary = "consultar postagem em uma turma", description = "consultar postagem em uma turma")
	@ApiResponse(responseCode = "200", description = "consulta bem sucedido")
	@ApiResponse(responseCode = "404", description = "autor ou turma não encontradas")
	@ApiResponse(responseCode = "422", description = "Usuario não pertence a essa turma")
	@GetMapping(value = "/turma/{idTurma}")
	public ResponseEntity<Page<PostagemResponse>> consultarPostagens(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhas", defaultValue = "10") Integer linhas,
			@RequestParam(value = "ordarPor", defaultValue = "datapublicacao") String ordarPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
			JwtAuthenticationToken token, @PathVariable Long idTurma){
		
		PageRequest pagi = PageRequest.of(pagina, linhas, Direction.valueOf(ordem), ordarPor);

		Page<PostagemResponse> resultado = service.consultarPostagem( idTurma, pagi, ExtratorId.extrair(token));

		return ResponseEntity.ok(resultado);
	}
	
	

	@Operation(summary = "excluir postagem em uma turma", description = "excluir postagem em uma turma")
	@ApiResponse(responseCode = "200", description = "exclusão bem sucedido")
	@ApiResponse(responseCode = "404", description = "autor ou turma não encontradas")@ApiResponse(responseCode = "404", description = "autor ou turma não encontradas")
	@ApiResponse(responseCode = "422", description = "postagem não pertence ao usuario ou não pentence a turma do id fornecido.")
	@DeleteMapping(value = "/turma/{idTurma}/postagem/{idPostagem}")
	public ResponseEntity<Page<PostagemResponse>> excluirPostagens(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhas", defaultValue = "10") Integer linhas,
			@RequestParam(value = "ordarPor", defaultValue = "datapublicacao") String ordarPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
			JwtAuthenticationToken token, @PathVariable Long idTurma, Long idPostagem){
		
		PageRequest pagi = PageRequest.of(pagina, linhas, Direction.valueOf(ordem), ordarPor);

		Page<PostagemResponse> resultado = service.excluirPostagem( idTurma, idPostagem, pagi, ExtratorId.extrair(token));

		return ResponseEntity.ok(resultado);
	}

}
