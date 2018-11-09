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
import com.churchOS.model.Celula;
import com.churchOS.repository.CelulaRepository;
import com.churchOS.service.CelulaService;

@RestController
@RequestMapping("/celulas")
public class CelulaResource {

	@Autowired
	private CelulaRepository celulaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CelulaService celulaService;
	
	@GetMapping
	public List<Celula> pesquisar() {
		return celulaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Celula> criar (@Valid @RequestBody Celula celula, HttpServletResponse response) {
		Celula celulaSalva = celulaRepository.save(celula);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, celulaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(celulaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Celula celula = celulaRepository.findOne(id);
		
		return celula == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(celula);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		celulaRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Celula> atualizar(@PathVariable Long id, @Valid @RequestBody Celula celula) {
		Celula celulaAtualizada = celulaService.atualizar(celula, id);
		
		return ResponseEntity.ok(celulaAtualizada);
	}
	
	
}
