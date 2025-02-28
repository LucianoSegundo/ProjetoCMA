package com.ifpe.projetoCMA.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mockitoSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;

import com.ifpe.projetoCMA.controller.dto.request.CadastroRequest;
import com.ifpe.projetoCMA.controller.dto.request.LoginRequest;
import com.ifpe.projetoCMA.controller.dto.response.CadastroResponse;
import com.ifpe.projetoCMA.controller.dto.response.LoginResponse;
import com.ifpe.projetoCMA.entity.Notificacao;
import com.ifpe.projetoCMA.entity.Papel;
import com.ifpe.projetoCMA.entity.Questionario;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.responta404.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.resposta400.AcessoNegadoException;
import com.ifpe.projetoCMA.exception.resposta422.CadastroNedadoException;
import com.ifpe.projetoCMA.repository.PapelRepository;
import com.ifpe.projetoCMA.repository.UsuarioRepository;


@SpringBootTest
@ActiveProfiles("test")
class UsuarioServiceTest {

	
	private static String email = "email@gmail.com";
	private static String usuario = "caio";
	private static String senha = "A123456!jk@";
	private static String nome = "caio";
	
	@Spy
	@InjectMocks
	private UsuarioService service;
	
	@Mock
	private UsuarioRepository userRepo;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	private JwtEncoder jwtCodificador;	
	
	@Mock
	private PapelRepository papelRepo;
	
	static Usuario user =new Usuario();
	@BeforeEach
	void criarUsuario() {
		// antes de  rodar qualquer teste, criar um usuário.
		 email = "e"+ email;
		 usuario = "c" + usuario;
	
		
			user.setId(1l);
			user.setUsuario(usuario);
			user.setNome(nome);
			user.setEmail(email);
			user.setSenha(senha);
			user.addPapel(new Papel("aluno", new HashSet<Usuario>()));		
			
		Mockito.when(bCryptPasswordEncoder.encode(user.getSenha())).thenReturn("1234");
		Mockito.when(bCryptPasswordEncoder.matches(senha, senha)).thenReturn(true);
		
		Mockito.when(this.userRepo.findByUsuario(user.getUsuario())).thenReturn(Optional.of(user));
		Mockito.when(this.userRepo.save(Mockito.any(Usuario.class))).thenReturn(user);
		Mockito.when(this.papelRepo.findByAutoridade(Mockito.anyString())).thenReturn(Optional.of(new Papel("aluno", new HashSet<>())));

		Mockito.when(this.userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		
	}
	
	@Test
	@DisplayName("Cadastrar Usuario Com Sucesso")
	void cadastrarUsuario(){
		
		CadastroRequest request = new CadastroRequest(nome, email, senha, usuario);

		CadastroResponse response = service.cadastroUsuario(request);
		
		assertThat(response).describedAs("Cadastro retornou nulo").isNotNull();
	}
	
	@Test
	@DisplayName("Cadastrar Usuario Com camposNulos")
	void cadastrarUsuarioCamposNulos(){

		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(null, email, senha, usuario);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "nome nulo não foi barradi");
		
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, null, senha, usuario);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "email nulo não foi barrado");
		
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, email, null, usuario);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "senha nulo não foi barrado");
		
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, email, senha, null);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "usuario nulo não foi barrado");
		
	}
	
	@Test
	@DisplayName("Cadastrar Usuario Com campos Blanck")
	void cadastrarUsuarioCamposBlanck(){

		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest("", email, senha, usuario);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "nome Blanck não foi barradi");
		
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, "", senha, usuario);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "email Blanck não foi barrado");
		
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, email, "", usuario);
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "senha Blanck não foi barrado");
		
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, email, senha, "");
			CadastroResponse response = service.cadastroUsuario(request);
			
		}, "usuario Blanck não foi barrado");
		
	}

	@Test
	@DisplayName("Cadastrar Usuario Que já Existe")
	void cadastrarUsuarioJaExiste(){
		assertThrows(CadastroNedadoException.class, () ->{
			
			Mockito.when(userRepo.existsByNome(Mockito.anyString())).thenReturn(true);

			CadastroRequest request = new CadastroRequest(nome, email, senha, usuario);
			service.cadastroUsuario(request);
			
			service.cadastroUsuario(request);
			
		}, "Usuario que já existe foi sobscrito");
	}
	
	@Test
	@DisplayName("Cadastrar Usuario Com Email já em Uso")
	void cadastrarUsuarioComEmailEmUso(){
		assertThrows(CadastroNedadoException.class, () ->{
			
			Mockito.when(userRepo.existsByEmail(Mockito.anyString())).thenReturn(true);
			
			CadastroRequest request = new CadastroRequest(nome, email, senha, usuario);
			service.cadastroUsuario(request);
			
			
		}, "Usuario que já existe foi sobscrito");
	}
	
	@Test
	@DisplayName("Cadastrar Usuario Com Senha Fraca")
	void cadastrarUsuarioComSenhaFraca(){
		assertThrows(CadastroNedadoException.class, () ->{
			
			CadastroRequest request = new CadastroRequest(nome, email, "ddkkdhd", usuario);
			service.cadastroUsuario(request);
			
		}, "Senha fraca foi permitida");
	}

	// Teste Login 
	
	@Test
	@DisplayName("Login Com Sucesso")
	void LoginComSucesso(){
			LoginResponse login = new LoginResponse("ddddddddd", 21l);
        Mockito.doReturn(login).when(service).gerarToken(Mockito.any(Usuario.class));

		
		LoginResponse response = service.Login(new LoginRequest(usuario, senha));
		
		assertThat(response).describedAs("Login retornou nulo").isNotNull();
	}
	
	@Test
	@DisplayName("Login Com Senha Errada")
	void LoginComSenhaErrada(){
		assertThrows(AcessoNegadoException.class, ()->{
			
			LoginResponse response = service.Login(new LoginRequest(usuario, "qualquer Senha"));
			
		}, "Senha que não bate foi aceita");
	}
	
	@Test
	@DisplayName("Login Com Compos Nulos")
	void LoginComCamposNulos(){
		assertThrows(AcessoNegadoException.class, ()->{
			
			LoginResponse response = service.Login(new LoginRequest(usuario, null));
			
		}, "Senha nula foi aceita");
		
		assertThrows(AcessoNegadoException.class, ()->{
			
			LoginResponse response = service.Login(new LoginRequest(null, senha));
			
		}, "login nula foi aceita");
		
	}
	
	@Test
	@DisplayName("Login Com Compos Blanck")
	void LoginComCamposBlanck(){
		assertThrows(AcessoNegadoException.class, ()->{
			
			LoginResponse response = service.Login(new LoginRequest(usuario, ""));
			
		}, "Senha Black foi aceita");
		
		assertThrows(AcessoNegadoException.class, ()->{
			
			LoginResponse response = service.Login(new LoginRequest("", senha));
			
		}, "login Blanck foi aceita");
		
	}
	
	@Test
	@DisplayName("Login Com Usuario Que não Existe")
	void LoginComUsuarioQueNãoExiste(){
		assertThrows(EntidadeNaoEncontradaException.class, ()->{
			
			LoginResponse response = service.Login(new LoginRequest("outro usuario", senha));
			
		}, "usuario que não existe foi aceito");
	}
}
