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
import com.churchOS.model.EscalaLouvor;
import com.churchOS.repository.EscalaLouvorRepository;
import com.churchOS.repository.filter.EscalaLouvorFilter;
import com.churchOS.repository.projection.ResumoEscalaLouvor;
import com.churchOS.service.EscalaLouvorService;

@RestController
@RequestMapping("/escala-louvor")
public class EscalaLouvorResource {

	@Autowired
	private EscalaLouvorRepository escalaLouvorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private EscalaLouvorService escalaLouvorService;
	
	@GetMapping
	public List<EscalaLouvor> pesquisar() {
		return escalaLouvorRepository.findAll();
	}
	
	@GetMapping(params = "resumo")
	public List<ResumoEscalaLouvor> resumir(EscalaLouvorFilter escalaLouvorFilter) {
		return escalaLouvorRepository.resumir(escalaLouvorFilter);
	}
	
	@PostMapping
	public ResponseEntity<EscalaLouvor> criar(@Valid @RequestBody EscalaLouvor escala, HttpServletResponse response) {
		EscalaLouvor escalaSalva =  escalaLouvorRepository.save(escala);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, escalaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(escalaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		EscalaLouvor escala = escalaLouvorRepository.findOne(id);
		
		return escala == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(escala);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		escalaLouvorRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EscalaLouvor> atualizar(@PathVariable Long id, @Valid @RequestBody EscalaLouvor escala) {
		EscalaLouvor escalaSalva = escalaLouvorService.atualizar(escala, id);
		return ResponseEntity.ok(escalaSalva);
	}
	
	
	
	
	
}
