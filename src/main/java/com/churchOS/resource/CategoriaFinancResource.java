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
import com.churchOS.model.CategoriaFinanceiro;
import com.churchOS.repository.CategoriaFinancRepository;
import com.churchOS.service.CategoriaFinancService;

@RestController
@RequestMapping("categoria-financeiro")
public class CategoriaFinancResource {
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private CategoriaFinancRepository categoriaFinancRepository;
	
	@Autowired
	private CategoriaFinancService categoriaFinancService;
	
	@GetMapping
	public List<CategoriaFinanceiro> pesquisar() {
		return categoriaFinancRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<CategoriaFinanceiro> criar(@Valid @RequestBody CategoriaFinanceiro categoriaFinanc, HttpServletResponse response) {
		CategoriaFinanceiro categoriaSalva = categoriaFinancRepository.save(categoriaFinanc);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		CategoriaFinanceiro categoriaSalva = categoriaFinancRepository.findOne(id);
		
		return categoriaSalva == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoriaSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaFinanceiro> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaFinanceiro categoria) {
		CategoriaFinanceiro categoriaSalva = categoriaFinancService.atualizar(categoria, id);
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		categoriaFinancRepository.delete(id);
	}
	
	
}
