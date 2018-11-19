package com.churchOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.churchOS.model.CategoriaFinanceiro;

public interface CategoriaFinancRepository extends JpaRepository<CategoriaFinanceiro, Long> {
	
}
