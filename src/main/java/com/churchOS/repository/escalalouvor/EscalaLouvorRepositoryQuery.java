package com.churchOS.repository.escalalouvor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.churchOS.model.EscalaLouvor;
import com.churchOS.repository.filter.EscalaLouvorFilter;
import com.churchOS.repository.projection.ResumoEscalaLouvor;

public interface EscalaLouvorRepositoryQuery {

	public Page<EscalaLouvor> filtrar(EscalaLouvorFilter escalaLouvorFilter, Pageable pageable);
	public List<ResumoEscalaLouvor> resumir(EscalaLouvorFilter escalaLouvorFilter);
	
}
