package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.Igreja;
import com.churchOS.repository.IgrejaRepository;

@Service
public class IgrejaService {

	@Autowired
	private IgrejaRepository igrejaRepository;
	
	public Igreja atualizar(Igreja igreja, Long id) {
		Igreja igrejaSalva = igrejaRepository.findOne(id);
		if (igrejaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(igreja, igrejaSalva, "id");
		return igrejaRepository.save(igrejaSalva);
	}
	
	
}
