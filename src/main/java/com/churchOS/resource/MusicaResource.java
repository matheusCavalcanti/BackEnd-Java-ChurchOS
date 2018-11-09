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
import com.churchOS.model.Musica;
import com.churchOS.repository.MusicaRepository;
import com.churchOS.repository.filter.MusicaFilter;
import com.churchOS.service.MusicaService;

@RestController
@RequestMapping("/musicas")
public class MusicaResource {
	
	@Autowired
	private MusicaRepository musicaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MusicaService musicaService;

	@GetMapping
	public List<Musica> pesquisar(MusicaFilter musicaFilter) {
		return musicaRepository.filtrar(musicaFilter);
	}
	
	@PostMapping
	public ResponseEntity<Musica> criar(@Valid @RequestBody Musica musica, HttpServletResponse response) {
		Musica musicaSalva = musicaRepository.save(musica);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, musicaSalva.getId()));
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(musicaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Musica musica = musicaRepository.findOne(id);
		
		return musica == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(musica);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		musicaRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Musica> atualizar(@PathVariable Long id, @Valid @RequestBody Musica musica) {
		Musica musicaSalva = musicaService.atualizar(musica, id);
		return ResponseEntity.ok(musicaSalva);
	}
	
	
	
	
}
