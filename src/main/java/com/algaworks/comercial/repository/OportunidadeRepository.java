package com.algaworks.comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.comercial.model.Oportunidade;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long>{
	
	Optional<Oportunidade> fundByDescricaoAndNomeProspecto(String descricao, String nomeProspecto);
}
