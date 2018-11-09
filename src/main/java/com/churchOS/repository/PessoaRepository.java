package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
