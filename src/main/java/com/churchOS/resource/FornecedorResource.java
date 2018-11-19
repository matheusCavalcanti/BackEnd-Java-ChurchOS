package com.churchOS.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.churchOS.event.RecursoCriadoEvent;
import com.churchOS.model.Fornecedor;
import com.churchOS.repository.FornecedorRepository;
import com.churchOS.service.FornecedorService;

@RestController
@RequestMapping("fornecedor")
public class FornecedorResource {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@GetMapping
	public List<Fornecedor> pesquisar() {
		return fornecedorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor forn, HttpServletResponse response) {
		Fornecedor fornecedorSalvo = fornecedorRepository.save(forn);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Fornecedor fornecedor = fornecedorRepository.findOne(id);
		
		return fornecedor == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(fornecedor);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> atualizar(@Valid @RequestBody Fornecedor forne, @PathVariable Long id) {
		Fornecedor fornecedorSalvo = fornecedorService.atualizar(forne, id);
		return ResponseEntity.ok(fornecedorSalvo);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		fornecedorRepository.delete(id);
	}
	
}
