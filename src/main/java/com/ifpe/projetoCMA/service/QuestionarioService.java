package com.ifpe.projetoCMA.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpe.projetoCMA.controller.dto.request.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.request.VackRequest;
import com.ifpe.projetoCMA.controller.dto.response.HonneyResponse;
import com.ifpe.projetoCMA.controller.dto.response.QuestionarioResponse;
import com.ifpe.projetoCMA.controller.dto.response.VackResponse;
import com.ifpe.projetoCMA.entity.QuestHonney;
import com.ifpe.projetoCMA.entity.QuestVack;
import com.ifpe.projetoCMA.entity.Questionario;
import com.ifpe.projetoCMA.exception.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.QuestionarioRepositorio;

@Service
public class QuestionarioService {

	private BCryptPasswordEncoder codSenha;
	private QuestionarioRepositorio questRepo;
	
	public QuestionarioService(QuestionarioRepositorio questRepo, BCryptPasswordEncoder codSenha) {
		this.questRepo = questRepo;
		this.codSenha = codSenha;
	}

	@Transactional
	public HonneyResponse responderHonney(HonneyRequest request, long userId) {
		if(VerificarCampos.verificarTemCamposNulos(request)) throw new operacaoNaoPermitidaException("nenhum campo pode ser nulo");
			
		Questionario quest = retornarQuest(userId);
		
		QuestHonney honney = quest.getQuestHonney();
		
		honney.preecher(request);
		
		quest.setQuestHonney(honney);
		
		quest = questRepo.save(quest);
	
		return quest.getQuestHonney().toHonneyResponse();
		
	}

	public VackResponse responderVack(VackRequest request, long userId) {
		if(VerificarCampos.verificarTemCamposNulos(request)) throw new operacaoNaoPermitidaException("nenhum campo pode ser nulo");
		
		Questionario quest = retornarQuest(userId);
		
		QuestVack vack = quest.getVack();
		
		vack.preecher(request);
		
		quest.setVack(vack);
		
		quest = questRepo.save(quest);
	
		return quest.getVack().toVackResponse();
	}
	
	@Transactional
	public QuestionarioResponse coletarQuestionario(long userId) {
		
		return retornarQuest(userId).toResponse();
	}
	
	@Transactional(readOnly = true)
	public Questionario retornarQuest(long id) {
		Questionario quest =questRepo.findByAutorId(id).orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possivel encontrar um questionário para este usuário"));

		return quest;
	}

	@Transactional
	public HonneyResponse deletarHonney(String senha, long userId) {
		if(senha == null || senha.isBlank()) throw new operacaoNaoPermitidaException("senha incorreta");
		Questionario quest = retornarQuest(userId);
		
		if (codSenha.matches(senha,quest.getAutor().getSenha()) == false ) throw new operacaoNaoPermitidaException("senha incorreta");
		
		QuestHonney honney = quest.getQuestHonney();
		
		honney.serar();
		
		quest.setQuestHonney(honney);
		
		quest = questRepo.save(quest);
		
		return quest.getQuestHonney().toHonneyResponse();
	};
	
	@Transactional
	public VackResponse deletarVack(String senha, long userId) {
		if(senha == null || senha.isBlank()) throw new operacaoNaoPermitidaException("senha em blanco ou nula");
		
			
		Questionario quest = retornarQuest(userId);
		if (codSenha.matches(senha,quest.getAutor().getSenha()) == false ) throw new operacaoNaoPermitidaException("senha incorreta");
		
		
		QuestVack vack = quest.getVack();
		
		vack.serar();
		
		quest.setVack(vack);
		
		quest = questRepo.save(quest);
		
		return quest.getVack().toVackResponse();
	};

}
