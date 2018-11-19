package com.churchOS.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.churchOS.event.RecursoCriadoEvent;
import com.churchOS.model.Contribuicao;
import com.churchOS.repository.ContribuicaoRepository;
import com.churchOS.service.ContribuicaoService;

@RestController
@RequestMapping("contribuicao")
public class ContribuicaoResource {

	@Autowired
	private ContribuicaoRepository contribuicaoRepository;
	
	@Autowired
	private ContribuicaoService contribuicaoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Contribuicao> pesquisar() {
		return contribuicaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Contribuicao> criar(@Valid @RequestBody Contribuicao contrib, HttpServletResponse response) {
		Contribuicao contribSalva = contribuicaoService.adicionar(contrib);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contribSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contribSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Contribuicao contribSalva = contribuicaoRepository.findOne(id);
		
		return contribSalva == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(contribSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contribuicao> atualizar(@Valid @RequestBody Contribuicao contrib, @PathVariable Long id) {
		Contribuicao contribSalva = contribuicaoService.atualizar(contrib, id);
		return ResponseEntity.ok(contribSalva);
	}
	
	@PutMapping("/excluir/{id}")
	public ResponseEntity<Contribuicao> remover(@Valid @RequestBody Contribuicao contrib) {
		Contribuicao contribExcluida = contribuicaoService.remover(contrib);
		contribuicaoRepository.delete(contribExcluida);
		
		return ResponseEntity.noContent().build();
	}
	
}
