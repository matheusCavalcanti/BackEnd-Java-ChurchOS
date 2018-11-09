package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.Musica;
import com.churchOS.repository.MusicaRepository;

@Service
public class MusicaService {
	
	@Autowired
	private MusicaRepository musicaRepository;

	public Musica atualizar(Musica musica, Long id) {
		Musica musicaSalva = musicaRepository.findOne(id);
		if (musicaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(musica, musicaSalva, "id");
		return musicaRepository.save(musicaSalva);
	}
	
	/*public Musica excluir(Long id) {
		Musica musicaDaLista = musicaRepository.findOne(id);
		List<ListaMusicas> listas = listaMusicaRepository.findAll();
		for (ListaMusicas lista : listas) {
			lista.getMusicas().remove(musicaDaLista);
		}
		return musicaDaLista;
	}*/
	 
}
