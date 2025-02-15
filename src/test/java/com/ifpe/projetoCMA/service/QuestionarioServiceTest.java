package com.ifpe.projetoCMA.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.ActiveProfiles;

import com.ifpe.projetoCMA.controller.dto.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.HonneyResponse;
import com.ifpe.projetoCMA.controller.dto.VackRequest;
import com.ifpe.projetoCMA.controller.dto.VackResponse;
import com.ifpe.projetoCMA.entity.Notificacao;
import com.ifpe.projetoCMA.entity.Papel;
import com.ifpe.projetoCMA.entity.Questionario;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.ausenciaDeDadosException;
import com.ifpe.projetoCMA.exception.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.PapelRepository;
import com.ifpe.projetoCMA.repository.UsuarioRepository;

import jakarta.transaction.Transactional;


@SpringBootTest
@ActiveProfiles("test")
class QuestionarioServiceTest {

	@Autowired
	private QuestionarioService service;
	
	@Autowired
	private  UsuarioRepository userRepo;
	
	private static Usuario user = new Usuario();

	@BeforeEach
	 void criarUsuario() {
		// antes de  rodar qualquer teste, criar um usuário.
		user.setEmail("email@gamil.com");
		user.addPapel(new Papel("aluno", new HashSet<Usuario>()));
		user.setSenha("Q1234567!");
		user.setUsuario("caio");
		user.setNome("henrique");
		user.setQuestionario(new Questionario());
		user.setNotificacao( new ArrayList<Notificacao>());
		
		user = userRepo.save(user);
		System.out.println(" Usuario Criado com sucesso");
		
		
	}
	
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

}
