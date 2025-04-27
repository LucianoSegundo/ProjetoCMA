package com.ifpe.projetoCMA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ifpe.projetoCMA.controller.dto.request.PostagemRequest;
import com.ifpe.projetoCMA.controller.dto.response.PostagemResponse;
import com.ifpe.projetoCMA.entity.Postagem;
import com.ifpe.projetoCMA.entity.Turma;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.responta404.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.resposta422.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.PostagemRepository;
import com.ifpe.projetoCMA.repository.TurmaRepositorio;
import com.ifpe.projetoCMA.repository.UsuarioRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository repositorio;

	@Autowired
	private TurmaRepositorio turmaRepo;

	@Autowired
	private UsuarioRepository userRepo;

	public PostagemService() {
	}

	public Page<PostagemResponse> criarPostagem(PostagemRequest postagem, Long idTurma, PageRequest pagi,
			Long idUsuario) {

		// verificando se a postagem é aceitavel

		if (postagem.titulo().isBlank() || postagem.titulo() == null)
			throw new operacaoNaoPermitidaException("Titulo não pode ser nulo");
		if (postagem.conteudo().isBlank() || postagem.conteudo() == null)
			throw new operacaoNaoPermitidaException("Conteudo não pode ser nulo");

		// coletando os objetos envolvidos e verificando se o usuario está presente.

		Turma turma = turmaRepo.findById(idTurma)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("A Turma não existe, não é possivel postar."));

		Usuario usuario = userRepo.findById(idUsuario)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("O usuario não existe, não é possivel postar."));

		if (turma.getProfessor().equals(usuario) == false && turma.getAlunos().contains(usuario) == false)
			throw new operacaoNaoPermitidaException("Usuario não pertence a essa turma.");

		// montando postagem

		Postagem novaPostagem = new Postagem();

		novaPostagem.setTitulo(postagem.titulo());
		novaPostagem.setConteudo(postagem.conteudo());

		usuario.addPostagem(novaPostagem);
		novaPostagem.setAutor(usuario);

		turma.addPostagem(novaPostagem);
		novaPostagem.setTurma(turma);

		repositorio.save(novaPostagem);

		// Retornano postagens da turma.

		Page<PostagemResponse> lista = repositorio.findAllByTurma_Id(idTurma, pagi).map(x -> x.toResponse());

		return lista;
	}

	public Page<PostagemResponse> consultarPostagem(Long idTurma, PageRequest pagi, Long idUsuario) {

		// coletando os objetos envolvidos e verificando se o usuario está presente.

		Turma turma = turmaRepo.findById(idTurma)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("A Turma não existe"));

		Usuario usuario = userRepo.findById(idUsuario)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("O usuario não existe"));

		if (turma.getProfessor().equals(usuario) == false && turma.getAlunos().contains(usuario) == false)
			throw new operacaoNaoPermitidaException("Usuario não pertence a essa turma.");

		Page<PostagemResponse> lista = repositorio.findAllByTurma_Id(idTurma, pagi).map(x -> x.toResponse());

		return lista;
	}

	public Page<PostagemResponse> excluirPostagem(Long idTurma, Long idPostagem, PageRequest pagi, Long idAutor) {

		Postagem postagem = repositorio.findById(idPostagem).orElseThrow(() -> new EntidadeNaoEncontradaException("A postagem não existe"));
		
		if(postagem.getTurma().getId() != idTurma) throw new operacaoNaoPermitidaException("A postagem não faz parte dessa turma.");

		if(postagem.getAutor().getId() != idAutor) throw new operacaoNaoPermitidaException("Só o autor pode excluir a propria postagem.");
		
		repositorio.deleteById(idPostagem);

		Page<PostagemResponse> lista = repositorio.findAllByTurma_Id(idTurma, pagi).map(x -> x.toResponse());

		return lista;
	}
}
