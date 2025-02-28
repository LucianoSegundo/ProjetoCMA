package com.ifpe.projetoCMA.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.ifpe.projetoCMA.controller.dto.request.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.request.VackRequest;
import com.ifpe.projetoCMA.controller.dto.response.HonneyResponse;
import com.ifpe.projetoCMA.controller.dto.response.QuestionarioResponse;
import com.ifpe.projetoCMA.controller.dto.response.VackResponse;
import com.ifpe.projetoCMA.entity.Notificacao;
import com.ifpe.projetoCMA.entity.Papel;
import com.ifpe.projetoCMA.entity.Questionario;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.resposta422.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.UsuarioRepository;


@SpringBootTest
@ActiveProfiles("test")
class QuestionarioServiceTest {

	@Autowired
	private QuestionarioService service;
	
	@Autowired
	private BCryptPasswordEncoder codSenha;

	@Autowired
	private  UsuarioRepository userRepo;
	
	private Usuario user = new Usuario();
	private static String acrescimo = "";
	private String senha = "Q1234567!";
	@BeforeEach
	void criarUsuario() {
		// antes de  rodar qualquer teste, criar um usuário.
		
		acrescimo +="a";
		
		System.out.println(" Usuario Criado com sucesso");
		user.setUsuario(acrescimo+"caio");
		user.setNome("henrique");
		user.setEmail(acrescimo+"email@gamil.com");
		user.setSenha(codSenha.encode(senha));
		user.addPapel(new Papel("aluno", new HashSet<Usuario>()));		
		user.setNotificacao( new ArrayList<Notificacao>());
		
		user = userRepo.save(user);
		
	
	}
	
	// Testar Criar QuestHonney
	@Test
	@DisplayName("criar QuestHonney Com Sucesso")
	void criarQuestHonneyComSucesso() {
		
		HonneyRequest request = new HonneyRequest(1, 2, 3, 4);
		
		HonneyResponse response = service.responderHonney(request, user.getId());
		
		assertThat(response).describedAs("Questionario Honney não foi cadastrado").isNotNull();
	}
	
	@Test
	@DisplayName("bloquear QuestHonney Com Campo Nulo")
	void bloquearQuestHonneyComCampoNulo() {
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			HonneyRequest request = new HonneyRequest(null, 2, 3, 4);

			HonneyResponse response = service.responderHonney(request, user.getId());

		}, "primeira posiçao não foi verificada");
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			HonneyRequest request = new HonneyRequest(1, null, 3, 4);
			
			HonneyResponse response = service.responderHonney(request, user.getId());

		}, "segunda posiçao não foi verificada");
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			HonneyRequest request = new HonneyRequest(1, 2, null, 4);
			
			HonneyResponse response = service.responderHonney(request, user.getId());

		}, "terceira posiçao não foi verificada");
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			HonneyRequest request = new HonneyRequest(1, 2, 3, null);
			
			HonneyResponse response = service.responderHonney(request, user.getId());

		}, "quarta posiçao não foi verificada");
	}
	
	// Testar Criar QuestVack

	@Test
	@DisplayName("criar QuestVack Com Sucesso")
	void criarQuestVackComSucesso() {
		
		VackRequest request = new VackRequest(1, 2, 3, 4);
		
		VackResponse response = service.responderVack(request, user.getId());
		
		assertThat(response).describedAs("Questionario Vack não foi cadastrado").isNotNull();
	}
	
	@Test
	@DisplayName("bloquear QuestVack Com Campo Nulo")
	void bloquearQuestvackComCampoNulo() {
		
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			VackRequest request = new VackRequest(null, 2, 3, 4);

			VackResponse response = service.responderVack(request, user.getId());

		}, "primeira posiçao não foi verificada");
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			VackRequest request = new VackRequest(1, null, 3, 4);

			VackResponse response = service.responderVack(request, user.getId());

		}, "segunda posiçao não foi verificada");
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			VackRequest request = new VackRequest(1, 2, null, 4);

			VackResponse response = service.responderVack(request, user.getId());

		}, "terceira posiçao não foi verificada");
		
		assertThrows(operacaoNaoPermitidaException.class,()-> {
			VackRequest request = new VackRequest(1, 2, 3, null);

			VackResponse response = service.responderVack(request, user.getId());

		}, "quarta posiçao não foi verificada");
	}

	// Testar Exclução de Questionario

	@Test
	@DisplayName("Excluir QuestHonney Com Sucesso")
	void excluirQuestHonney() {
		HonneyRequest request = new HonneyRequest(1, 2, 3, 4);
	
		 service.responderHonney(request, user.getId());
		
		 HonneyResponse response = service.deletarHonney(senha, user.getId());
		 
		 assertThat(response).describedAs("QuestHonney retornou nulo").isNotNull();
		 assertThat(response.ativo()).describedAs("Ativo não foi excluido").isEqualTo(0);
		 assertThat(response.pragmatico()).describedAs("Pragmatico não foi excluido").isEqualTo(0);
		 assertThat(response.referido()).describedAs("Referido não foi excluido").isEqualTo(0);
		 assertThat(response.teorico()).describedAs("Teorico não foi excluido").isEqualTo(0);

		 assertThat(response.estilo()).describedAs("").isEqualTo("Sem estilo definido");

	}
	
	@Test
	@DisplayName("Excluir QuestHonney Com Senha Errada")
	void excluirQuestHonneyComSenhaErrada() {
		HonneyRequest request = new HonneyRequest(1, 2, 3, 4);
		
		 service.responderHonney(request, user.getId());
		 
		 assertThrows(operacaoNaoPermitidaException.class, () ->{
			HonneyResponse response = service.deletarHonney("qualquer coisa", user.getId());			
		} ,"a Exclusão foi realizada mesmo com a senha errada");
		
	}
	
	@Test
	@DisplayName("Excluir QuestVack Com Sucesso")
	void excluirQuestVack() {
		
		 VackRequest request = new VackRequest(1, 2, 3, 4);
		
		 service.responderVack(request, user.getId());
		
		 VackResponse response = service.deletarVack(senha, user.getId());
		 
		 assertThat(response).describedAs("QuestVack retornou nulo").isNotNull();
		 assertThat(response.auditivo()).describedAs("auditivo não foi excluido").isEqualTo(0);
		 assertThat(response.cinestesico()).describedAs("cinestesico não foi excluido").isEqualTo(0);
		 assertThat(response.visuak()).describedAs("visual não foi excluido").isEqualTo(0);
		 assertThat(response.leituraEscrita()).describedAs("leituraEscrita não foi excluido").isEqualTo(0);
		 assertThat(response.estilo()).describedAs("Estilo não foi excluido").isEqualTo("Sem estilo definido");

	}
	
	@Test
	@DisplayName("Excluir QuestVack Com Senha Errada")
	void excluirQuestVackComSenhaErrada() {
		

		VackRequest request = new VackRequest(1, 2, 3, 4);
		
		 service.responderVack(request, user.getId());
		 
		 assertThrows(operacaoNaoPermitidaException.class, () ->{
			VackResponse response = service.deletarVack("qualquer coisa", user.getId());			
		} ,"a Exclusão foi realizada mesmo com a senha errada");
		
	}
	
	// Testar coleta de Questionários
	
	@Test
	@DisplayName("Consultar Questionarios Com Senha Errada")
	void ConsultarQuestionarios() {
		QuestionarioResponse response = service.coletarQuestionario(user.getId());
		
		assertThat(response).describedAs("questionário retornou nulo").isNotNull();
		assertThat(response.questVack()).describedAs("QuestVack retornou nulo").isNotNull();
		assertThat(response.questHonney()).describedAs("QuestHonney retornou nulo").isNotNull();

	}
	
	
	
}
