package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.Musica;
import com.churchOS.repository.musica.MusicaRepositoryQuery;

public interface MusicaRepository extends JpaRepository<Musica, Long>, MusicaRepositoryQuery {

}
