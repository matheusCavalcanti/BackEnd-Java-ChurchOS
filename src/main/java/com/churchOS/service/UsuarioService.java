package com.churchOS.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.Usuario;
import com.churchOS.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario atualizar(Usuario usuario, Long id) {
		Usuario usuarioSalvo = usuarioRepository.findOne(id);
		if (usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(usuario, usuarioSalvo, "id");
		return usuarioRepository.save(usuarioSalvo);
	}
	
}
