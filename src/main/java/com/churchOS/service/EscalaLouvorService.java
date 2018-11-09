package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.EscalaLouvor;
import com.churchOS.repository.EscalaLouvorRepository;

@Service
public class EscalaLouvorService {

	@Autowired
	private EscalaLouvorRepository escalaLouvorRepository;
	
	public EscalaLouvor atualizar(EscalaLouvor escala, Long id) {
		EscalaLouvor escalaSalva = escalaLouvorRepository.findOne(id);
		if (escalaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(escala, escalaSalva, "id");
		return escalaLouvorRepository.save(escalaSalva);
	}
	
}
