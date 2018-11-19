package com.churchOS.service;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.ContaBancaria;
import com.churchOS.model.Despesa;
import com.churchOS.repository.ContaBancariaRepository;
import com.churchOS.repository.DespesaRepository;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ContaBancariaRepository bancariaRepository;
	
	public Despesa adicionar(Despesa despesa) {
		ContaBancaria conta = bancariaRepository.findOne(despesa.getConta().getId());
		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal valorDespesa = despesa.getValor();
		BigDecimal novoValor = saldoAtual.subtract(valorDespesa);
		
		ContaBancaria contaAtualizada = bancariaRepository.findOne(despesa.getConta().getId());
		contaAtualizada.setSaldo(novoValor);
		BeanUtils.copyProperties(conta, contaAtualizada, "id");
		bancariaRepository.save(contaAtualizada);
		
		return despesaRepository.save(despesa);
	}
	
	public Despesa atualizar(Despesa despesa, Long id) {
		Despesa despesaSalva = despesaRepository.findOne(id);
		if (despesaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(despesa, despesaSalva, "id");
		return despesaRepository.save(despesaSalva);
	}
	
}
