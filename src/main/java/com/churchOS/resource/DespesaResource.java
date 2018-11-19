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
import com.churchOS.model.Despesa;
import com.churchOS.repository.DespesaRepository;
import com.churchOS.service.DespesaService;

@RestController
@RequestMapping("despesas")
public class DespesaResource {

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private DespesaService despesaService;
	
	@GetMapping
	public List<Despesa> pesquisar() {
		return despesaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Despesa> criar(@Valid @RequestBody Despesa despesa, HttpServletResponse response) {
		Despesa despesaSalva = despesaService.adicionar(despesa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, despesaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Despesa despesaSalva = despesaRepository.findOne(id);
		
		return despesaSalva == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(despesaSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @Valid @RequestBody Despesa despesa) {
		Despesa despesaSalva = despesaService.atualizar(despesa, id);
		return ResponseEntity.ok(despesaSalva);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		despesaRepository.delete(id);
	}
	
}
