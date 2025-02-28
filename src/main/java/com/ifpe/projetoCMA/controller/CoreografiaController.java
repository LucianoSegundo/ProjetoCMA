package com.ifpe.projetoCMA.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.projetoCMA.controller.dto.request.CoreografiaRequest;
import com.ifpe.projetoCMA.controller.dto.response.CoreografiaResponse;
import com.ifpe.projetoCMA.service.CoreografiaService;
import com.ifpe.projetoCMA.service.ExtratorId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/api/v1/coreografia")
public class CoreografiaController {
	
	private CoreografiaService service;
	
	public CoreografiaController(CoreografiaService service) {
		this.service = service;
		
	}

	@Operation(summary = "Rota de criação de Coreografiar", description = "nehum dos valores requerido deve ser branco ou nulo.")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "422", description = "A criação da coreografia foi negada")
	@ApiResponse(responseCode = "404", description = "Usuario não encontrado")
	@ApiResponse(responseCode = "406", description = "já existe uma coreografia com este nome para este usuario")
	@PostMapping(value = "/")
	public ResponseEntity<CoreografiaResponse> criarCoreografia(JwtAuthenticationToken token, @RequestBody CoreografiaRequest coreo ){
		CoreografiaResponse response = service.criarCoreografia(ExtratorId.extrair(token), coreo);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Rota de edição de Coreografias", description = "campos brancos ou nulos seram ignoradoos, apenas os campos com novos valores atribuidos seram alterados")
	@ApiResponse(responseCode = "200", description = "coreografia não encontrada")
	@ApiResponse(responseCode = "404", description = "A criação da coreografia foi negada")
	@PutMapping(value = "/alterar/{coreoId}")
	public ResponseEntity<CoreografiaResponse> alterarCoreografia(JwtAuthenticationToken token,@RequestBody CoreografiaRequest coreo, @PathVariable Long coreoId ){
		CoreografiaResponse response = service.alterarCoreografia(ExtratorId.extrair(token), coreo, coreoId);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Rota de deleção de Coreografias", description = "Deleção de coreografia, ao deletar ela será perdida para sempre")
	@ApiResponse(responseCode = "200", description = "deleção bem sucedida")
	@ApiResponse(responseCode = "404", description = "entidade não foi encontrada")
	@ApiResponse(responseCode = "401", description = "senha incorreta")
	@ApiResponse(responseCode = "422", description = "permissão de deleção negada, causas posssiveis, Id do usuario ou coreografia nulo, senha incorreta ou senha nula")
	@DeleteMapping(value = "/deletar/{coreoId}")
	public ResponseEntity<Void> deletarCoreografia(JwtAuthenticationToken token,@RequestBody String senha, @PathVariable Long coreoId ){
		
		service.deletarCoreografia(ExtratorId.extrair(token), senha, coreoId);
		
		return ResponseEntity.ok().build();
	}
	

	@Operation(summary = "Rota de consulta de Coreografias", description = "Consulta de coreografia, retorna a coreografia se ela pertencer ao usuário dono do token")
	@ApiResponse(responseCode = "200", description = "consulta bem sucedida  ")
	@ApiResponse(responseCode = "404", description = "entidade não foi encontrada")
	@GetMapping(value = "/{coreoId}")
	public ResponseEntity<CoreografiaResponse> consultarCoreografia(JwtAuthenticationToken token, @PathVariable Long coreoId ){
		CoreografiaResponse response = service.consultarCoreografia(ExtratorId.extrair(token), coreoId);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Rota de listargem de Coreografias", description = "retorna uma lista paginada de coreografia")
	@ApiResponse(responseCode = "200", description = "consulta bem sucedida  ")
	@GetMapping(value = "/listar")
	public ResponseEntity<Page<CoreografiaResponse>> listarCoreografia(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhas", defaultValue = "10") Integer linhas,
			@RequestParam(value = "ordarPor", defaultValue = "nome") String ordarPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
			JwtAuthenticationToken token ){
		
		PageRequest pagi = PageRequest.of(pagina, linhas, Direction.valueOf(ordem), ordarPor);

		Page<CoreografiaResponse> resultado = service.listarCoreografia( ExtratorId.extrair(token), pagi);

		return ResponseEntity.ok(resultado);
	}
}
