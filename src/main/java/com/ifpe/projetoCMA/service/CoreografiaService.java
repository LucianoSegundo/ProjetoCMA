package com.ifpe.projetoCMA.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpe.projetoCMA.controller.dto.request.CoreografiaRequest;
import com.ifpe.projetoCMA.controller.dto.response.CoreografiaResponse;
import com.ifpe.projetoCMA.entity.Coreografia;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.outrasRespostas.CredenciaisInvalidasException;
import com.ifpe.projetoCMA.exception.responta404.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.resposta422.CamposBrancosOuNulosException;
import com.ifpe.projetoCMA.exception.resposta422.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.CoreoRepository;
import com.ifpe.projetoCMA.repository.UsuarioRepository;

@Service
public class CoreografiaService {

	private UsuarioRepository userRepo;
	private CoreoRepository coreoRepo;
	private BCryptPasswordEncoder encoder;
	
	public CoreografiaService(UsuarioRepository userRepo, CoreoRepository coreoRepo, BCryptPasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.coreoRepo = coreoRepo;
		this.encoder = encoder;
	}

	@Transactional
	public CoreografiaResponse criarCoreografia(Long userID, CoreografiaRequest coreo) {
		
		Usuario usuario = userRepo.findById(userID).orElseThrow(( ) -> new EntidadeNaoEncontradaException("De alguma forma este usuário não existe"));
		
		if(VerificarCampos.verificarTemCamposNulos(coreo)) throw new CamposBrancosOuNulosException("Nenhum dos campos pode ser nulo");
		
		if(coreo.nome().isBlank() ||coreo.aprendizado().isBlank() || coreo.conhecimento().isBlank() ) throw new CamposBrancosOuNulosException("Nenhum dos campos pode estar vazio ou em blanco");

		Coreografia coreografia = new Coreografia(usuario, coreo);
		
		Coreografia response = coreoRepo.save(coreografia);
		
		return response.toresponse();
	}

	@Transactional
	public CoreografiaResponse alterarCoreografia(Long userID, CoreografiaRequest coreo, Long coreoID) {
		boolean modificado = false;
		
		if(userID == null || coreoID == null)  throw new CamposBrancosOuNulosException("de alguma forma o id do usuário ou o id da coreografia chegaram nulos.");

		Coreografia coreografia = coreoRepo.findByIdAndAutorId(coreoID,userID).orElseThrow(( ) -> new EntidadeNaoEncontradaException("Coreografia não existe ou não pertence a esse usuário"));
		
		if(coreo.nome() != null && coreo.nome().isBlank() == false) {
			coreografia.setNome(coreo.nome());
			modificado = true;
		}
		
		if(coreo.aprendizado() != null && coreo.aprendizado().isBlank() == false) {
			coreografia.setAprendizado(coreo.aprendizado());
			modificado = true;
		}
		
		if(coreo.conhecimento() != null && coreo.conhecimento().isBlank() == false) {
			coreografia.setConhecimento(coreo.conhecimento());
			
			modificado = true;
		}
		
		if(modificado = true) coreografia = coreoRepo.save(coreografia);
			
		
		return coreografia.toresponse();
	}

	@Transactional
	public void deletarCoreografia(Long userID, String senha, Long coreoID) {

	if(userID == null || coreoID == null)  throw new CamposBrancosOuNulosException("de alguma forma o id do usuário ou o id da coreografia chegaram nulos.");
		
	if(senha == null || senha.isBlank())  throw new CamposBrancosOuNulosException("Senha não pode ser nula");
	
	Coreografia coreografia = coreoRepo.findByIdAndAutorId(coreoID,userID).orElseThrow(( ) -> new EntidadeNaoEncontradaException("Coreografia não existe ou não pertence a esse usuário"));
	
	Usuario autor = coreografia.getAutor();
	
	if(encoder.matches(senha, autor.getSenha())) coreoRepo.delete(coreografia);
	else throw new CredenciaisInvalidasException("Deleção negada, senha incorreta");
	
	}

	@Transactional(readOnly = true)
	public CoreografiaResponse consultarCoreografia(Long userID, Long coreoID) {
		
		if(userID == null || coreoID == null)  throw new CamposBrancosOuNulosException("de alguma forma o id do usuário ou o id da coreografia chegaram nulos.");

		Coreografia coreografia = coreoRepo.findByIdAndAutorId(coreoID,userID).orElseThrow(( ) -> new EntidadeNaoEncontradaException("Coreografia não existe ou não pertence a esse usuário"));
		
		return coreografia.toresponse();
	}

	@Transactional(readOnly = true)
	public Page<CoreografiaResponse> listarCoreografia(Long userID, PageRequest pagina) {

		if(userID == null)  throw new CamposBrancosOuNulosException("de alguma forma o id do usuário ou o id da coreografia chegaram nulos.");
		
		Page<Coreografia> resposta = coreoRepo.findAllByAutorId(userID,pagina);
		
		return resposta.map(x ->x.toresponse());
	}

}
