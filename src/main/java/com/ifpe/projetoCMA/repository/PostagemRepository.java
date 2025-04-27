package com.ifpe.projetoCMA.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.projetoCMA.controller.dto.response.PostagemResponse;
import com.ifpe.projetoCMA.entity.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	Page<Postagem> findAllByTurma_Id(Long idTurma, PageRequest pagi);

}
