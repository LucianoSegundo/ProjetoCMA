package com.ifpe.projetoCMA.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpe.projetoCMA.entity.Coreografia;
import com.ifpe.projetoCMA.entity.Usuario;

public interface CoreoRepository extends JpaRepository<Coreografia, Long> {

	Optional<Coreografia> findByIdAndAutorId(Long coreoID, Long userID);

	Page<Coreografia> findAllByAutorId(Long userID, PageRequest pagina);

}
