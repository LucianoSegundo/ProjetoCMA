package com.ifpe.projetoCMA.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.projetoCMA.entity.Atividades;

public interface AtividadeReposito extends JpaRepository<Atividades, Long> {

	Boolean existsByNomeAndCoreografiaId(String nome, Long coreoId);
	
	Optional<Atividades> findByIdAndCoreografiaId(Long atividadeId,Long coreoId);

	Page<Atividades> findAllByCoreografiaId(Long coreoId, PageRequest pagina);

}
