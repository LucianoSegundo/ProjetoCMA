package com.ifpe.projetoCMA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifpe.projetoCMA.controller.dto.request.AtividadeRequest;
import com.ifpe.projetoCMA.controller.dto.response.AtividadeResponse;
import com.ifpe.projetoCMA.entity.Atividades;
import com.ifpe.projetoCMA.entity.Coreografia;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.outrasRespostas.CredenciaisInvalidasException;
import com.ifpe.projetoCMA.exception.outrasRespostas.recursoJaExisteException;
import com.ifpe.projetoCMA.exception.responta404.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.resposta422.CamposBrancosOuNulosException;
import com.ifpe.projetoCMA.exception.resposta422.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.AtividadeReposito;
import com.ifpe.projetoCMA.repository.CoreoRepository;

@Service
public class AtividadeService {
	@Autowired
	private CoreoRepository coreoRepo;

	@Autowired
	private AtividadeReposito repositorio;

	@Autowired
	private BCryptPasswordEncoder encoder;
	public AtividadeService() {
	}

	public AtividadeResponse criarAtividades(Long userID, Long coreoID, AtividadeRequest atividade) {

		if (coreoID == null)
			throw new CamposBrancosOuNulosException("Id da coreografia se encontra nulo");

		Coreografia coreografia = coreoRepo.findByIdAndAutorId(coreoID, userID).orElseThrow(
				() -> new EntidadeNaoEncontradaException("Coreografia não existe ou não pertence a esse usuário"));

		if (atividade.nome() == null || atividade.nome().isBlank())
			throw new CamposBrancosOuNulosException("Nome da atividade não pode ser nulo");

		if (repositorio.existsByNomeAndCoreografiaId(atividade.nome(), coreografia.getId()) == true)
			throw new recursoJaExisteException("coreografia já tem essa atividade");

		Atividades vidade = new Atividades();

		vidade.setNome(atividade.nome());
		vidade.setConteudo(atividade.conteudo());

		coreografia.adicionarAtividade(vidade);
		vidade.setCoreografia(coreografia);
		vidade = repositorio.save(vidade);

		return new AtividadeResponse(vidade.getId(), vidade.getNome(), vidade.getConteudo());
	}

	public AtividadeResponse adicionarConteudo(Long userID, Long coreoId, Long atividadeId, List<String> conteudos) {
		if (coreoId == null)
			throw new CamposBrancosOuNulosException("Id da coreografia se encontra nulo");
		if (atividadeId == null)
			throw new CamposBrancosOuNulosException("Id da atividade se encontra nulo");

		Atividades atividade = repositorio.findByIdAndCoreografiaId(atividadeId, coreoId).orElseThrow(
				() -> new EntidadeNaoEncontradaException("Atividade não existe ou não pertence a esse usuário"));
		
		if(atividade.getCoreografia().getAutor().getId() != userID) throw new operacaoNaoPermitidaException("a atividade não pertence ao usuário");
		for (String conteudo : conteudos) {
			
			if(conteudo != null && conteudo.isBlank()==false) atividade.adicionarConteudo(conteudo);
		}
		atividade = repositorio.save(atividade);
		
		return new AtividadeResponse(atividade.getId(), atividade.getNome(), atividade.getConteudo());
	}

	public void excluirAtividade(Long userID, Long coreoId, Long atividadeId, String senha) {
		if (coreoId == null)
			throw new CamposBrancosOuNulosException("Id da coreografia se encontra nulo");
		if (atividadeId == null)
			throw new CamposBrancosOuNulosException("Id da atividade se encontra nulo");
		if (senha == null)
			throw new CamposBrancosOuNulosException("Senha necessária para excluir está nula");

		Atividades atividade = repositorio.findByIdAndCoreografiaId(atividadeId, coreoId).orElseThrow(
				() -> new EntidadeNaoEncontradaException("Atividade não existe ou não pertence a esse usuário"));
		
		Usuario autor = atividade.getCoreografia().getAutor();
		
		if(autor.getId() != userID) throw new operacaoNaoPermitidaException("a atividade não pertence ao usuário");
		
		if(encoder.matches(senha, autor.getSenha())) repositorio.delete(atividade);
		else throw new CredenciaisInvalidasException("Deleção negada ");
	}

	public Page<AtividadeResponse> listarAtividades(Long userId, PageRequest pagina, Long coreoId) {
		if(coreoId == null)  throw new CamposBrancosOuNulosException("O id da coreografia chegaram nulos.");

		Page<Atividades> resposta = repositorio.findAllByCoreografiaId(coreoId,pagina);
		
		return resposta.map(x -> new AtividadeResponse(x.getId(),x.getNome(), x.getConteudo()));
	}

}
