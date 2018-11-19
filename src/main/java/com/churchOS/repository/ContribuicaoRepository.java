package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.Contribuicao;

public interface ContribuicaoRepository extends JpaRepository<Contribuicao, Long> {

}
