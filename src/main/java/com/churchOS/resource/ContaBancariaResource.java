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
import com.churchOS.model.ContaBancaria;
import com.churchOS.repository.ContaBancariaRepository;
import com.churchOS.service.ContaBancariaService;

@RestController
@RequestMapping("conta-bancaria")
public class ContaBancariaResource {

	@Autowired
	private ContaBancariaRepository bancariaRepository;
	
	@Autowired
	private ContaBancariaService bancariaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<ContaBancaria> pesquisar() {
		return bancariaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ContaBancaria> criar(@Valid @RequestBody ContaBancaria conta, HttpServletResponse response) {
		ContaBancaria contaSalva = bancariaRepository.save(conta);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		ContaBancaria conta = bancariaRepository.findOne(id);
		
		return conta == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(conta);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContaBancaria> atualizar(@Valid @RequestBody ContaBancaria conta, @PathVariable Long id) {
		ContaBancaria contaSalva = bancariaService.atualizar(conta, id);
		return ResponseEntity.ok(contaSalva);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		bancariaRepository.delete(id);
	}
	
	
	
	
	
}
