package com.ifpe.projetoCMA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.projetoCMA.entity.Questionario;

public interface QuestionarioRepositorio extends JpaRepository<Questionario, Long> {

	Optional<Questionario> findByAutorId(Long userId);

}
