package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.EscalaLouvor;
import com.churchOS.repository.escalalouvor.EscalaLouvorRepositoryQuery;

public interface EscalaLouvorRepository extends JpaRepository<EscalaLouvor, Long>, EscalaLouvorRepositoryQuery {

}
