package com.ifpe.projetoCMA.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.projetoCMA.controller.dto.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.HonneyResponse;
import com.ifpe.projetoCMA.controller.dto.QuestionarioResponse;
import com.ifpe.projetoCMA.controller.dto.VackRequest;
import com.ifpe.projetoCMA.controller.dto.VackResponse;
import com.ifpe.projetoCMA.service.QuestionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/api/v1/questionario")
public class QuestionarioController {
	
	private QuestionarioService questiServi;
	
	public QuestionarioController(QuestionarioService questiServi) {
		
		this.questiServi = questiServi;
	}
	
	
	@Operation( summary = "Rota para o registro do Questionario Honney" , description = "Rota requer autenticação, deve ser anexado o token de autenticação, adiquirido no momento do login, ao cabeçalho da requisição..")
	@ApiResponse(responseCode = "200", description = "cadastro bem sucedido")
	@ApiResponse(responseCode = "400", description = "cadastro negado devido a campos nulos")
	@PostMapping(value = "/honney")
	public ResponseEntity<HonneyResponse> responderHonney(JwtAuthenticationToken token, @RequestBody HonneyRequest request){
		try {
			
			long userId = Long.parseLong(token.getName());
			HonneyResponse response = questiServi.responderHonney(request, userId);
			
			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Operation( summary = "Rota para o registro do Questionario Vack" , description = "Rota requer autenticação, vincula o questionario Vack enviado id de usuario presente no token")
	@ApiResponse(responseCode = "200", description = "cadastro bem sucedido")
	@ApiResponse(responseCode = "400", description = "cadastro negado devido a campos nulos")	
	@PostMapping(value = "/vack")
	public ResponseEntity<VackResponse> responderVack(JwtAuthenticationToken token, @RequestBody VackRequest request){
		try {
			
			long userId = Long.parseLong(token.getName());
			VackResponse response = questiServi.responderVack(request, userId);
			
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Operation( summary = "Rota para coletar o questionario do usuario" , description = "Rota requer autenticação, deve ser anexado o token de autenticação, adiquirido no momento do login, ao cabeçalho da requisição. retorna ambos os questionarios")
	@ApiResponse(responseCode = "200", description = "coleta bem sucedido")
	@GetMapping(value = "/")
	public ResponseEntity<QuestionarioResponse> coletarQuestionario(JwtAuthenticationToken token){
		
		QuestionarioResponse response = questiServi.coletarQuestionario(0);
		
		return ResponseEntity.ok(response);
	}
	
	
	@Operation( summary = "Rota para deletar o questionario HonneyAlonso do usuario" , description = "Rota requer autenticação, serve para deletar o questionario honneyAlonso do usuario, deixando o espaço com um questionario zerado, recebe a senha do usuario no corpo do requisição")
	@ApiResponse(responseCode = "200", description = "exclusão bem sucedido")
	@ApiResponse(responseCode = "401", description = "exclusão negada")
	@DeleteMapping(value = "/honney")
	public ResponseEntity deletarHonney(JwtAuthenticationToken token, @RequestBody String senha) {
		try {
			
			long userId = Long.parseLong(token.getName());

			questiServi.deletarHonney( senha, userId );
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			throw e;
		}
		
	}


	@Operation( summary = "Rota para deletar o questionario Vack do usuario" , description = "Rota requer autenticação, serve para deletar o questionario vack do usuario, deixando o espaço com um questionario zerado, recebe a senha do usuario no corpo do requisição")
	@ApiResponse(responseCode = "200", description = "exclusão bem sucedido")
	@ApiResponse(responseCode = "401", description = "exclusão negada")
	@DeleteMapping(value = "/vack")
	public ResponseEntity deletarVack(JwtAuthenticationToken token, @RequestBody String senha) {
		try {
			
			long userId = Long.parseLong(token.getName());

			questiServi.deletarVack( senha, userId );
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
}
