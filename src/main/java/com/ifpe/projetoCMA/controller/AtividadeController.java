package com.ifpe.projetoCMA.controller;

import java.util.List;

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

import com.ifpe.projetoCMA.controller.dto.request.AtividadeRequest;
import com.ifpe.projetoCMA.controller.dto.response.AtividadeResponse;
import com.ifpe.projetoCMA.controller.dto.response.CoreografiaResponse;
import com.ifpe.projetoCMA.service.AtividadeService;
import com.ifpe.projetoCMA.service.ExtratorId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/atividade")
public class AtividadeController {

	@Autowired
	private AtividadeService service;
	
	public AtividadeController(){}
	
	@Operation(summary = "Rota de criação de atividades para uma coreografia", description = " Recebe uma lista de atividade, que é composta por um nome e uma lista de conteudos e vincula a uma coreografia")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "406", description = "já existe uma atividade com este nome para este coreografia")
	@ApiResponse(responseCode = "422", description = "id da coreografia nulo")
	@ApiResponse(responseCode = "404", description = "Usuario ou coreografia não foram encontrados")
	@PostMapping("/{coreoId}")
	public ResponseEntity<AtividadeResponse> criarAtividades(JwtAuthenticationToken token, @PathVariable Long coreoId, @RequestBody AtividadeRequest atividades){
		
		AtividadeResponse response = service.criarAtividades(ExtratorId.extrair(token),coreoId, atividades);
		
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Rota de adição de conteudo para uma atividade atividades", description = " Recebe uma lista de conteudo,")
	@ApiResponse(responseCode = "200", description = "criação bem sucedido")
	@ApiResponse(responseCode = "422", description = "ID da atividade ou da coreografia podem estaar nulos, coreografia com este id pode não pertencer ao usuário ")
	@ApiResponse(responseCode = "404", description = "Usuario ou coreografia não foram encontrados")
	@PostMapping("/{coreoId}/{atividadeId}")
	public ResponseEntity<AtividadeResponse> adicionarConteudo(JwtAuthenticationToken token, @PathVariable Long coreoId,@PathVariable Long atividadeId, @RequestBody List<String> conteudo ){
		
		AtividadeResponse response = service.adicionarConteudo(ExtratorId.extrair(token),coreoId, atividadeId, conteudo);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Rota de deleção atividade", description = " Recebe a senha do usuário para confirmar a deleção")
	@ApiResponse(responseCode = "200", description = "deleção bem sucedido")
	@ApiResponse(responseCode = "422", description = "ID da atividade ou da coreografia podem estaar nulos, ")
	@ApiResponse(responseCode = "404", description = "Atividade não foi encontrados")
	@ApiResponse(responseCode = "401", description = "senha incorreta")
	@DeleteMapping("/{coreoId}/{atividadeId}")
	public ResponseEntity<Void> excluirAtividade(JwtAuthenticationToken token, @PathVariable Long coreoId,@PathVariable Long atividadeId, @RequestBody String senha ){
		
		 service.excluirAtividade(ExtratorId.extrair(token),coreoId, atividadeId, senha);
		
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Rota de listargem de Coreografias", description = "retorna uma lista paginada de coreografia")
	@ApiResponse(responseCode = "200", description = "consulta bem sucedida  ")
	@ApiResponse(responseCode = "422", description = "Id da coreografia está nula")
	@GetMapping(value = "/{coreoId}/listar")
	public ResponseEntity<Page<AtividadeResponse>> listarCoreografia(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhas", defaultValue = "10") Integer linhas,
			@RequestParam(value = "ordarPor", defaultValue = "nome") String ordarPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem,
			JwtAuthenticationToken token, @PathVariable Long coreoId ){
		
		PageRequest pagi = PageRequest.of(pagina, linhas, Direction.valueOf(ordem), ordarPor);

		Page<AtividadeResponse> resultado = service.listarAtividades( ExtratorId.extrair(token), pagi, coreoId);

		return ResponseEntity.ok(resultado);
	}
}
