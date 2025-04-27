package com.ifpe.projetoCMA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.projetoCMA.entity.Comentario;
import com.ifpe.projetoCMA.entity.Coreografia;
import com.ifpe.projetoCMA.entity.Postagem;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.responta404.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.exception.resposta422.operacaoNaoPermitidaException;
import com.ifpe.projetoCMA.repository.ComentarioRepository;
import com.ifpe.projetoCMA.repository.CoreoRepository;
import com.ifpe.projetoCMA.repository.PostahemRepository;
import com.ifpe.projetoCMA.repository.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository repositorio;

	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private CoreoRepository coreoRepo;

	@Autowired
	private PostahemRepository postRepo;

	public ComentarioService() {
	}

	public List<Comentario> ResponderComentario(Long idcomentario, String mensagem, Long idAutor) {

		if (mensagem.isBlank() || mensagem == null)
			throw new operacaoNaoPermitidaException("Mensagem não pode ser branca ou nula");

		// criando comentario
		Comentario comentario = new Comentario();

		// recuperando comentario pai
		Comentario comentarioPai = repositorio.findById(idcomentario).orElseThrow(
				() -> new EntidadeNaoEncontradaException("O comentário pai não existe, não é possivel comentar."));

		// adicionando comentario aos comentarios filhos.
		List<Comentario> lista = comentarioPai.getComentariosfilhos();
		lista.add(comentario);
		comentarioPai.setComentariosfilhos(lista);

		// adicionando comentario pai
		comentario.setComentariopai(comentarioPai);
		comentario.setMensagem(mensagem);

		// comentando
		comentar(idAutor, comentario);

		// retornando lista atualizada
		return repositorio.findAllByComentariopai_Id(idcomentario);
	}

	public List<Comentario> comentarCoreografia(Long idCoreografia, String mensagem, Long idAutor) {

		if (mensagem.isBlank() || mensagem == null)
			throw new operacaoNaoPermitidaException("Mensagem não pode ser branca ou nula");

		// criando comentario
		Comentario comentario = new Comentario();

		// recuperando comentario pai
		Coreografia coreo = coreoRepo.findById(idCoreografia).orElseThrow(
				() -> new EntidadeNaoEncontradaException("a COREOGRAFIA	 não existe, não é possivel comentar."));

		// adicionar comentario a coreografia
		List<Comentario> lista = coreo.getComentarios();
		lista.add(comentario);
		coreo.setComentarios(lista);

		comentario.setCoreografia(coreo);
		comentario.setMensagem(mensagem);

		comentar(idAutor, comentario);

		// retornando lista atualizada
		return repositorio.findAllByCoreografia_Id(idCoreografia);

	}

	public List<Comentario> comentarPostagem(Long idPostagem, String mensagem, Long idAutor) {

		if (mensagem.isBlank() || mensagem == null)
			throw new operacaoNaoPermitidaException("Mensagem não pode ser branca ou nula");

		// criando comentario
		Comentario comentario = new Comentario();

		// recuperando comentario pai
		Postagem postagem = postRepo.findById(idPostagem).orElseThrow(
				() -> new EntidadeNaoEncontradaException("a Postagem	 não existe, não é possivel comentar."));

		// adicionar comentario a postagem
		List<Comentario> lista = postagem.getComentarios();
		lista.add(comentario);
		postagem.setComentarios(lista);

		comentario.setPostagempai(postagem);
		comentario.setMensagem(mensagem);

		comentar(idAutor, comentario);

		// retornando lista atualizada
		return repositorio.findAllByPostagempai_Id(idPostagem);
	}

	private void comentar(long idAutor, Comentario comentario) {

		Usuario usuario = userRepo.findById(idAutor)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não existe, não é possivel comentar."));

		// adicionar comentario a lista do autor;
		List<Comentario> lista = usuario.getComentarios();

		lista.add(comentario);
		usuario.setComentarios(lista);

		comentario.setAutor(usuario);

		// salvando comentario
		repositorio.save(comentario);

	}

	public List<Comentario> ConsultarComentarioPostagem(Long idPostagem) {
		// TODO Auto-generated method stub
		return repositorio.findAllByPostagempai_Id(idPostagem);
	}

	
	public List<Comentario> ConsultarComentarioComentario(Long idcomentario) {
		return repositorio.findAllByComentariopai_Id(idcomentario);
	}

	public List<Comentario> ConsultarComentarioCoreografia(Long idCoreografia) {
		// TODO Auto-generated method stub
		return repositorio.findAllByCoreografia_Id(idCoreografia);
	}

}
