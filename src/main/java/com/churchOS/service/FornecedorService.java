package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.Fornecedor;
import com.churchOS.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	public Fornecedor atualizar(Fornecedor forne, Long id) {
		Fornecedor fornecedorSalvo = fornecedorRepository.findOne(id);
		if (fornecedorSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(forne, fornecedorSalvo, "id");
		return fornecedorRepository.save(fornecedorSalvo);
	}
	
}
