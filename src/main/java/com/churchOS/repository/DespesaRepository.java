package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

}
