package com.ifpe.projetoCMA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.projetoCMA.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	List<Comentario> findAllByComentariopai_Id(Long idAutor);

	List<Comentario> findAllByCoreografia_Id(Long coreoid);

	List<Comentario> findAllByPostagempai_Id(Long idPostagem);

}
