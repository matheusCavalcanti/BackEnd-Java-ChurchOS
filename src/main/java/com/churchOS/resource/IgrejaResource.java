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
import com.churchOS.model.Igreja;
import com.churchOS.repository.IgrejaRepository;
import com.churchOS.service.IgrejaService;

@RestController
@RequestMapping("/igrejas")
public class IgrejaResource {
	
	@Autowired
	private IgrejaRepository igrejaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private IgrejaService igrejaService;

	@GetMapping
	public List<Igreja> pesquisar() {
		return igrejaRepository.findAll(); 
	}
	
	@PostMapping
	public ResponseEntity<Igreja> criar(@Valid @RequestBody Igreja igreja, HttpServletResponse response) {
		Igreja igrejaSalva = igrejaRepository.save(igreja);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, igrejaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(igrejaSalva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Igreja igreja = igrejaRepository.findOne(id);
		
		return igreja == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(igreja);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		igrejaRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Igreja> atualizar(@PathVariable Long id, @Valid @RequestBody Igreja igreja) {
		Igreja igrejaSalva = igrejaService.atualizar(igreja, id);
		return ResponseEntity.ok(igrejaSalva);
	}
	
	
}
