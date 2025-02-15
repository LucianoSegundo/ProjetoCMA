package com.ifpe.projetoCMA.service;

import org.springframework.stereotype.Service;

import com.ifpe.projetoCMA.controller.dto.HonneyRequest;
import com.ifpe.projetoCMA.controller.dto.HonneyResponse;
import com.ifpe.projetoCMA.controller.dto.QuestionarioResponse;
import com.ifpe.projetoCMA.controller.dto.VackRequest;
import com.ifpe.projetoCMA.controller.dto.VackResponse;

@Service
public class QuestionarioService {

	public HonneyResponse responderHonney(HonneyRequest request, long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public VackResponse responderVack(VackRequest request, long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public QuestionarioResponse coletarQuestionario(long userId) {
		
		return null;
	}

	public HonneyResponse deletarHonney(String senha, long userId) {
		// TODO Auto-generated method stub
		return null;
	};
	
	public VackResponse deletarVack(String senha, long userId) {
		// TODO Auto-generated method stub
		return null;
	};

}
