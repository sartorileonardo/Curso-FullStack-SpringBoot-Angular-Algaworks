package com.algaworks.comercial.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.comercial.model.Oportunidade;
import com.algaworks.comercial.repository.OportunidadeRepository;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {
	
	@Autowired
	private OportunidadeRepository oportunidadeRepository;

	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> listarPorId(@PathVariable Long id) {
		Optional<Oportunidade> oportunidade = oportunidadeRepository.findById(id);

		if(oportunidade == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oportunidade.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade inserir(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidadeRepository
				.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
		if(oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe uma oportunidade para este prospecto com a mesma descrição, se deseja alterar, utilize-o");
		}
		
		return oportunidadeRepository.save(oportunidade);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Oportunidade alterar(@Valid @RequestBody Oportunidade oportunidade, @PathVariable Long id) {
		Optional<Oportunidade> oportunidadeExistente = oportunidadeRepository
				.findById(id);
		
		if(!oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Não existe uma oportunidade para este prospecto, insira-o antes de alterá-lo");
		}
		oportunidade.setId(id);
		return oportunidadeRepository.save(oportunidade);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void remover(@PathVariable Long id) {
		Optional<Oportunidade> oportunidadeExistente = oportunidadeRepository
				.findById(id);
		
		if(!oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Não existe uma oportunidade para este prospecto, só é possível remover oportunidades existentes.");
		}
		
		if(id != null) {
			oportunidadeRepository.deleteById(id);
		}
		
	}
		
	
}
