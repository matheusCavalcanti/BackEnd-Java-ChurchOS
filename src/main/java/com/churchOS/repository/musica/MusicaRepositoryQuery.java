package com.churchOS.repository.musica;

import java.util.List;

import com.churchOS.model.Musica;
import com.churchOS.repository.filter.MusicaFilter;

public interface MusicaRepositoryQuery {

	public List<Musica> filtrar(MusicaFilter musicaFilter);
	
}
