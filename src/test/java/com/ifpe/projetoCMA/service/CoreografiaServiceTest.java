package com.ifpe.projetoCMA.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifpe.projetoCMA.controller.dto.request.CoreografiaRequest;
import com.ifpe.projetoCMA.controller.dto.response.CoreografiaResponse;
import com.ifpe.projetoCMA.entity.Coreografia;
import com.ifpe.projetoCMA.entity.Papel;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.outrasRespostas.CredenciaisInvalidasException;
import com.ifpe.projetoCMA.exception.responta404.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.resposta422.CamposBrancosOuNulosException;
import com.ifpe.projetoCMA.exception.resposta422.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.CoreoRepository;
import com.ifpe.projetoCMA.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test")
class CoreografiaServiceTest {

	@Mock
	private UsuarioRepository userRepo;
	
	@Mock
	private CoreoRepository coreoRepo;
	
	@Mock
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@InjectMocks
	private CoreografiaService coreoServi;
	
	static String senha = "1234";
	static Long userId = 1l;
	static Long coreoId = 2l;
	
	static Usuario user;
	static Coreografia coreo;
	
	static CoreografiaRequest request =  new CoreografiaRequest("nome", "aprendizado","conhecimento");

	static Page page;
	static PageRequest pagi;

	@BeforeEach
	 void prepararTestes() {
	
		// retorno da consulta do usuario
		if(user != null) {}
		
		user = new Usuario();
		user.setId(userId);
		user.setUsuario("usuario");
		user.setNome("usuario");
		user.setEmail("usuario@gmail.com");
		user.setSenha(senha);
		user.addPapel(new Papel("aluno", new HashSet<Usuario>()));	
		
		
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		
		// retorno da coreografia do usuario
		
			coreo = new Coreografia(user, request);
			coreo.setId(coreoId);
			
			Mockito.when(this.coreoRepo.save(new Coreografia(user, request))).thenReturn(coreo);
			Mockito.when(this.coreoRepo.findByIdAndAutorId(coreoId,userId)).thenReturn(Optional.of(coreo));


	
	}

	 void mokarPaginacao() {
		// preparando o retorno do lista paginada.
			
			List lista = new ArrayList<Coreografia>();
			lista.add(coreo);
			
			 Integer pagina = 0;
			 Integer linhas = 10;
			 String ordarPor= "nome";
			 String ordem = "ASC";
			
			pagi = PageRequest.of(pagina, linhas, Direction.valueOf(ordem), ordarPor);

			page = new PageImpl<Coreografia>(lista,pagi , 1);
			
			Mockito.when(coreoRepo.findAllByAutorId(userId, pagi )).thenReturn(page);
			
			 
	 }
	
	@Test
	@DisplayName("Criar Coreografia Com Sucesso")
	void criarCoreoComSucesso() {

		CoreografiaResponse response = coreoServi.criarCoreografia(userId, request);
		
		assertThat(response).as("objeto criado retornou nulo").isNotNull();
		assertTrue(response.nome().equals(request.nome()), "nome não bate com o enviado");
		assertTrue(response.aprendizado().equals(request.aprendizado()), "apredizado não bate com o enviado");
		assertTrue(response.conhecimento().equals(request.conhecimento()), "conhecimento não bate com o enviado");
		assertTrue(response.atividades() != null, "lista de atividades retornou nulo");
		assertTrue(response.questionario() != null, "questionaro retornou nulo");

	}
	
	@Test
	@DisplayName("Criar Com Campos nulus")
	void criarComCamposNulos() {
		
	assertThrows(CamposBrancosOuNulosException.class,()-> {
			CoreografiaRequest request2  = new CoreografiaRequest(null, "aprendizado","conhecimento");

			CoreografiaResponse response = coreoServi.criarCoreografia(userId, request2);

		}, "Cadastro com nome nulo foi permitido");
	
	assertThrows(CamposBrancosOuNulosException.class,()-> {
		CoreografiaRequest request2  = new CoreografiaRequest("nome", "aprendizado",null);

		CoreografiaResponse response = coreoServi.criarCoreografia(userId, request2);

	}, "Cadastro com conhecimento nulo foi permitido");
	
	
		
	}
	
	@Test
	@DisplayName("Criar Com Campos Brancos")
	void criarComCamposBrancos() {
		
	assertThrows(CamposBrancosOuNulosException.class,()-> {
			CoreografiaRequest request2  = new CoreografiaRequest("", "aprendizado","conhecimento");

			CoreografiaResponse response = coreoServi.criarCoreografia(userId, request2);

		}, "Cadastro com nome em blanco foi permitido");
	
	assertThrows(CamposBrancosOuNulosException.class,()-> {
		CoreografiaRequest request2  = new CoreografiaRequest("nome", "aprendizado","");

		CoreografiaResponse response = coreoServi.criarCoreografia(userId, request2);

	}, "Cadastro com conhecimento em blanco foi permitido");
		
	}
	
	@Test
	@DisplayName("criar Com Usuario Não Cadastrado")
	void criarComUsuarioNooCadastrado() {
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.criarCoreografia(7l, request);

		}, "usuario não existe");

	}

	@Test
	@DisplayName("Aleterar Coreografia Com Sucesso")
	void AleterarCoreografiaComSucesso() {
		
		CoreografiaRequest request2 = new CoreografiaRequest("nome2", "conhecimento2", "aprendizado2");
		
		Coreografia coreolocal = coreo;
		
		coreolocal.setNome(request2.nome());
		coreolocal.setConhecimento(request2.conhecimento());
		coreolocal.setAprendizado(request2.aprendizado());
		
		Mockito.when(this.coreoRepo.save(coreolocal)).thenReturn(coreolocal);

		
		CoreografiaResponse response =	coreoServi.alterarCoreografia(userId, request2, coreoId);
	
		assertThat(response.nome()).as("Nome não foi alterado").isEqualTo(request2.nome());
		assertThat(response.aprendizado()).as("Aprendizado não foi alterado").isEqualTo(request2.aprendizado());
		assertThat(response.conhecimento()).as( "Conhecimento não foi alterado").isEqualTo(request2.conhecimento());

	}
	
	@Test
	@DisplayName("Aleterar Apenas 1 Campo da Coreografia Com Sucesso")
	void alterarApenas1CampoDaCoreograia() {
		CoreografiaRequest request2 = new CoreografiaRequest("nome2", null, null);
		
		Coreografia coreolocal = coreo;
		
		coreolocal.setNome(request2.nome());
		coreolocal.setAprendizado(request.aprendizado());
		
		Mockito.when(this.coreoRepo.save(coreolocal)).thenReturn(coreolocal);

		
		CoreografiaResponse response =	coreoServi.alterarCoreografia(userId, request2, coreoId);
	
		assertThat(response.nome()).as("Nome não foi alterado").isEqualTo(request2.nome());
		assertThat(response.aprendizado()).as("Aprendizado não foi alterado").isEqualTo(request.aprendizado());
		assertThat(response.conhecimento()).as( "Conhecimento não foi alterado").isEqualTo(request.conhecimento());

	}
	
	@Test
	@DisplayName("Aleterar Apenas 1 Campo da Coreografia Com Sucesso")
	void nãoAlterarCoreograia() {
		CoreografiaRequest request2 = new CoreografiaRequest(null, null, null);
		
		Coreografia coreolocal = coreo;
				
		Mockito.when(this.coreoRepo.save(coreolocal)).thenReturn(coreolocal);

		
		CoreografiaResponse response =	coreoServi.alterarCoreografia(userId, request2, coreoId);
	
		assertThat(response.nome()).as("Nome não foi alterado").isNotNull();
		assertThat(response.aprendizado()).as("Aprendizado não foi alterado").isNotNull();
		assertThat(response.conhecimento()).as( "Conhecimento não foi alterado").isNotNull();

	}
	
	@Test
	@DisplayName("Usar Id de Usuario Ou de Coreografia Invalidas")
	void UsarUsuarioouCoreografiaInvalidas() {
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.alterarCoreografia(4l, request, 7l);

		}, "não foi lançada excessão para usuario e coreografia invalidos");
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.alterarCoreografia(userId, request, 7l);

		}, "não foi lançada excessão para coreografia invalida");
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.alterarCoreografia(4l, request, coreoId);

		}, "não foi lançada excessão para usuario invalido");
	}
	
	@Test
	@DisplayName("Usar Id de Usuario Ou de Coreografia Invalidas")
	void UsarIdUsuarioOuCoreografiaNulos() {
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			CoreografiaResponse response = coreoServi.alterarCoreografia(null, request, null);

		}, "não foi lançada excessão para usuario e coreografia invalidos");
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			CoreografiaResponse response = coreoServi.alterarCoreografia(userId, request, null);

		}, "não foi lançada excessão para coreografia invalida");
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			CoreografiaResponse response = coreoServi.alterarCoreografia(null, request, coreoId);

		}, "não foi lançada excessão para usuario invalido");
	}

	@Test
	@DisplayName("deletar Com Sucesso")
	void deletarComSucesso() {
		
		Mockito.when(this.bCryptPasswordEncoder.matches(senha, senha)).thenReturn(true);
		
		coreoServi.deletarCoreografia(userId, senha, coreoId);
	}
	
	@Test
	@DisplayName("deletar Com Ids Nulos")
	void deletarComIdsNulos() {
		
		assertThrows(CamposBrancosOuNulosException.class, ()->{	
			coreoServi.deletarCoreografia(null, senha, coreoId);
		}, "Id de usuário nulo não foi barrado");
		assertThrows(CamposBrancosOuNulosException.class, ()->{	
			coreoServi.deletarCoreografia(userId, senha, null);
		}, "Id da coreografia nulo não foi barrado");
		assertThrows(CamposBrancosOuNulosException.class, ()->{	
			coreoServi.deletarCoreografia(null, senha, null);
		}, "nunhum dos ids nulos foi barrado");
		
	}
	
	@Test
	@DisplayName("deletar Com Senha Invalida")
	void deletarComSenhaInvalida() {
		
		assertThrows(CamposBrancosOuNulosException.class, ()->{	
			coreoServi.deletarCoreografia(userId, "", coreoId);
		}, "Senha em blanco não foi barrada");
		
		assertThrows(CamposBrancosOuNulosException.class, ()->{	
			coreoServi.deletarCoreografia(userId, null, coreoId);
		}, "Senha nula não foi barrada");
		
		
		
	}
	
	@Test
	@DisplayName("deletar Com Senha Incorreta")
	void deletarComSenhaIncorreta() {
		
		assertThrows(CredenciaisInvalidasException.class, ()->{	
			coreoServi.deletarCoreografia(userId, "senha falsa", coreoId);
		}, "Senha que incorreta foi ignorada");
		
		
		
	}

	@Test
	@DisplayName("Consultar Coreografia Com Sucesso")
	void consultarCoreografiaComSucesso() {

		CoreografiaResponse response = coreoServi.consultarCoreografia(userId, coreoId);
		
		assertThat(response).as("objeto retornou nulo").isNotNull();
		assertThat(response.aprendizado()).as("aprendizado diverge do esperado").isEqualTo(response.aprendizado());
		assertThat(response.conhecimento()).as("conhecimento diverge do esperado").isEqualTo(response.conhecimento());
		assertThat(response.nome()).as("nome diverge do esperado").isEqualTo(response.nome());
		assertThat(response.questionario()).as("nome diverge do esperado").isNotNull();
		assertThat(response.atividades()).as("nome diverge do esperado").isNotNull();
		
	}
	
	@Test
	@DisplayName("Consultar Coreografia Com Ids Nulos")
	void consultarCoreografiaComIdsNulos() {
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			CoreografiaResponse response = coreoServi.consultarCoreografia(null, coreoId);

		}, "id de usuario nulo não foi barrado");
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			CoreografiaResponse response = coreoServi.consultarCoreografia(userId, null);

		}, "id de coreografia nulo não foi barrado");
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			CoreografiaResponse response = coreoServi.consultarCoreografia(null, null);

		}, "ids nulos não foi barrado");
		
	}
	
	@Test
	@DisplayName("Consultar Coreografia Com Ids não cadastrados")
	void consultarCoreografiaComIdsNaoCadastrados() {
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.consultarCoreografia(13l, coreoId);

		}, "id de usuario falso não foi barrado");
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.consultarCoreografia(userId, 14l);

		}, "id de coreografia falso não foi barrado");
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			CoreografiaResponse response = coreoServi.consultarCoreografia(13l, 14l);

		}, "ids falsos não foi barrado");
		
	}

	void testarPaginaçãocomIdNulo() {
		
		assertThrows(CamposBrancosOuNulosException.class,()-> {

			Page<CoreografiaResponse> response = coreoServi.listarCoreografia(null, pagi);

		}, "id de usuario nulo não foi barrado");

	}
	
	void testarPaginaçãocomIdcomIdinvalidado() {
		
		assertThrows(EntidadeNaoEncontradaException.class,()-> {

			Page<CoreografiaResponse> response = coreoServi.listarCoreografia(982l, pagi);

		}, "id de usuario nulo não foi barrado");

	}
	
	
}
