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
import com.churchOS.model.Usuario;
import com.churchOS.repository.UsuarioRepository;
import com.churchOS.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> pesquisar() {
		return usuarioRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findOne(id);
		
		return usuario == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		usuarioRepository.delete(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario usuarioSalvo = usuarioService.atualizar(usuario, id);	
		return ResponseEntity.ok(usuarioSalvo);
	}
	
	
	
	
}
