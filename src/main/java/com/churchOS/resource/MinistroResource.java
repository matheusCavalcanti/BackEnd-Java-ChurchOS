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
import com.churchOS.model.Ministro;
import com.churchOS.repository.MinistroRepository;
import com.churchOS.service.MinistroService;

@RestController
@RequestMapping("/ministros")
public class MinistroResource {

	@Autowired
	private MinistroRepository ministroRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MinistroService ministroService;
	
	@GetMapping
	public List<Ministro> pesquisar() {
		return ministroRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Ministro> criar(@Valid @RequestBody Ministro ministro, HttpServletResponse response) {
		Ministro ministroSalvo = ministroRepository.save(ministro);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, ministro.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ministroSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Ministro ministro = ministroRepository.findOne(id);
		
		return ministro == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ministro);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		ministroRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Ministro> atualizar(@PathVariable Long id, @Valid @RequestBody Ministro ministro) {
		Ministro ministroSalvo = ministroService.atualizar(ministro, id);
		return ResponseEntity.ok(ministroSalvo);
	}
	
}
