package com.ifpe.projetoCMA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.projetoCMA.entity.Postagem;

public interface PostahemRepository extends JpaRepository<Postagem, Long> {

}
