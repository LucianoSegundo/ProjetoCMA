package com.ifpe.projetoCMA.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.projetoCMA.controller.dto.request.CadastroRequest;
import com.ifpe.projetoCMA.controller.dto.request.LoginRequest;
import com.ifpe.projetoCMA.controller.dto.response.CadastroResponse;
import com.ifpe.projetoCMA.controller.dto.response.LoginResponse;
import com.ifpe.projetoCMA.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/api/v1/usuario")
public class UsuarioController {
	
	UsuarioService userServi;

	
	public UsuarioController(UsuarioService userServi) {
		this.userServi = userServi;
	}

	
	@Operation(summary = "Rota Login de Usuário", description = "Recebe String nome, String senha, retorna o token que permite o acesso ao resto da api.")
	@ApiResponse(responseCode = "200", description = "login bem sucedido")
	@ApiResponse(responseCode = "400", description = "Login mal sucedido")
	@ApiResponse(responseCode = "404", description = "O usuário fornecido não foi encontrado")

	@PostMapping(value = "/login")
	public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest request){
		
		LoginResponse response = userServi.Login(request);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Rota Cadastro de Usuário", description = "Recebe String nome, String senha, String Email retorna o o nome do usuário  por formalidade. O email deve seguir a e regex ^[A-Za-z0-9+_.-]+@(.+)$. Enquanto a senha deve conter no minimo 8 numeros, no minimo 1 numero e no minimo 1 caracter especial ")
	@ApiResponse(responseCode = "200", description = "cadastro bem sucedido")
	@ApiResponse(responseCode = "401", description = "o cadastro foi negado, um dos possiveis motivos é retornado como mensagem")
	@ApiResponse(responseCode = "500", description = "Os papeis não estão cadastrados no banco de dados, então não puderam ser recuperados para a realização do cadastro.")

	@PostMapping(value = "/")
	public ResponseEntity<CadastroResponse> cadastro(@RequestBody CadastroRequest request){
		
		CadastroResponse response = userServi.cadastroUsuario(request);
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Listagem de Usuários", description = "Faz a listagem de usuários, posteriormente será substituido por uma paginação.")
	@ApiResponse(responseCode = "200", description = "cadastro bem sucedido")
	@GetMapping(value = "/listagem")
	public ResponseEntity<List<CadastroResponse>> Lista(JwtAuthenticationToken token ){
		
		List<CadastroResponse> response = userServi.ListarUsuarios();
		
		return ResponseEntity.ok(response);
	}
	
}

