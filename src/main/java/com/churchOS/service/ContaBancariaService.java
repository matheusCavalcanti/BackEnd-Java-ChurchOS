package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.ContaBancaria;
import com.churchOS.repository.ContaBancariaRepository;

@Service
public class ContaBancariaService {

	@Autowired
	private ContaBancariaRepository bancariaRepository;
	
	public ContaBancaria atualizar(ContaBancaria conta, Long id) {
		ContaBancaria contaSalva = bancariaRepository.findOne(id);
		if (contaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(conta, contaSalva, "id");
		return bancariaRepository.save(contaSalva);
	}
	
}
