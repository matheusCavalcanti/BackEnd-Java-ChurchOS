package com.churchOS.service;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.churchOS.model.ContaBancaria;
import com.churchOS.model.Contribuicao;
import com.churchOS.model.ContribuicaoExcluida;
import com.churchOS.repository.ContaBancariaRepository;
import com.churchOS.repository.ContribuicaoExcluidaRepository;
import com.churchOS.repository.ContribuicaoRepository;
import com.churchOS.util.DataHora;

@Service
public class ContribuicaoService {

	@Autowired
	private ContribuicaoRepository repository;
	
	@Autowired
	private ContaBancariaRepository bancariaRepository;
	
	@Autowired
	private ContribuicaoExcluidaRepository excluidaRepository;
	
	public Contribuicao adicionar(Contribuicao contrib) {
		ContaBancaria conta = bancariaRepository.findOne(contrib.getConta().getId());
		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal valorContrib = contrib.getValor();
		BigDecimal novoValor = saldoAtual.add(valorContrib);
		
		ContaBancaria contaAtualizada = bancariaRepository.findOne(contrib.getConta().getId());
		contaAtualizada.setSaldo(novoValor);
		BeanUtils.copyProperties(conta, contaAtualizada, "id");
		bancariaRepository.save(contaAtualizada);
		
		return repository.save(contrib);
	}
	
	public Contribuicao atualizar(Contribuicao contrib, Long id) {
		Contribuicao contribSalva= repository.findOne(id);
		if (contribSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(contrib, contribSalva, "id");
		return repository.save(contribSalva);
	}
	
	public Contribuicao remover(Contribuicao contrib) {
		DataHora dataHora = new DataHora();
		
		ContribuicaoExcluida contExcluida = new ContribuicaoExcluida();
		contExcluida.setPessoa(contrib.getPessoa().getNome());
		contExcluida.setData(dataHora.getData());
		contExcluida.setValor(contrib.getValor());
		contExcluida.setMotivo(contrib.getMotivoExclusao());
		excluidaRepository.save(contExcluida);
		
		ContaBancaria conta = bancariaRepository.findOne(contrib.getConta().getId());
		BigDecimal valorAtual = conta.getSaldo();
		BigDecimal valorExcluido = contExcluida.getValor();
		BigDecimal novoValor = valorAtual.subtract(valorExcluido);
		conta.setSaldo(novoValor);
		bancariaRepository.save(conta);
		
		return contrib;
	}
	
	
}
