package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.CategoriaFinanceiro;
import com.churchOS.repository.CategoriaFinancRepository;

@Service
public class CategoriaFinancService {

	@Autowired
	private CategoriaFinancRepository categoriaFinancRepository;
	
	public CategoriaFinanceiro atualizar(CategoriaFinanceiro categoria, Long id) {
		CategoriaFinanceiro categoriaSalva = categoriaFinancRepository.findOne(id);
		if (categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaFinancRepository.save(categoriaSalva);
	}
	
}
