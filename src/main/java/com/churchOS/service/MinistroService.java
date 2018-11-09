package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.Ministro;
import com.churchOS.repository.MinistroRepository;

@Service
public class MinistroService {

	@Autowired
	private MinistroRepository ministroRepository;
	
	public Ministro atualizar(Ministro ministro, Long id) {
		Ministro ministroSalvo = ministroRepository.findOne(id);
		if (ministroSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(ministro, ministroSalvo, "id");
		return ministroRepository.save(ministroSalvo);
	}
	
}
