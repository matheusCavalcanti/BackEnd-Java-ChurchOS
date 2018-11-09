package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.Celula;
import com.churchOS.repository.CelulaRepository;

@Service
public class CelulaService {

	@Autowired
	private CelulaRepository celulaRepository;
	
	public Celula atualizar(Celula celula, Long id) {
		Celula celulaSalva = celulaRepository.findOne(id);
		if (celulaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(celula, celulaSalva, "id");
		return celulaRepository.save(celulaSalva);
	}
	
}
