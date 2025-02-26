package com.ifpe.projetoCMA.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpe.projetoCMA.controller.dto.request.CoreografiaRequest;
import com.ifpe.projetoCMA.controller.dto.response.CoreografiaResponse;

@Service
public class CoreografiaService {

	
	@Transactional
	public CoreografiaResponse criarCoreografia(Long userID, CoreografiaRequest coreo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public CoreografiaResponse alterarCoreografia(Long userID, CoreografiaRequest coreo, Long coreoID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void deletarCoreografia(Long userID, String senha, Long coreoId) {
		// TODO Auto-generated method stub
		
	}

	@Transactional(readOnly = true)
	public CoreografiaResponse consultarCoreografia(Long userID, Long coreoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	public Page<CoreografiaResponse> listarCoreografia(Long userID, PageRequest pagina) {
		// TODO Auto-generated method stub
		return null;
	}

}
