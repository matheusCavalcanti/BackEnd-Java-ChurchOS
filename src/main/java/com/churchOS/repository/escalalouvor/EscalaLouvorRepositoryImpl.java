package com.churchOS.repository.escalalouvor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.churchOS.model.EscalaLouvor;
import com.churchOS.repository.filter.EscalaLouvorFilter;
import com.churchOS.repository.projection.ResumoEscalaLouvor;

public class EscalaLouvorRepositoryImpl implements EscalaLouvorRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<EscalaLouvor> filtrar(EscalaLouvorFilter escalaLouvorFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EscalaLouvor> criteria = builder.createQuery(EscalaLouvor.class);
		Root<EscalaLouvor> root = criteria.from(EscalaLouvor.class);
		
		//criar as restrições
		Predicate[] predicates = criarRestricoes(escalaLouvorFilter, builder, root);
		criteria.where(predicates);
		
		
		TypedQuery<EscalaLouvor> query = manager.createQuery(criteria);
		adicionarRetricoesDePaginacao(query, pageable); 
		
		return new PageImpl<>(query.getResultList(), pageable, total(escalaLouvorFilter));
	}


	@Override
	public List<ResumoEscalaLouvor> resumir(EscalaLouvorFilter escalaLouvorFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoEscalaLouvor> criteria = builder.createQuery(ResumoEscalaLouvor.class);
		Root<EscalaLouvor> root = criteria.from(EscalaLouvor.class);
		
		criteria.select(builder.construct(ResumoEscalaLouvor.class
				, root.get("id"), root.get("descricao"), root.get("tipoCulto"), root.get("data")
				, root.get("ministros")
				, root.get("musicas")));
		
		Predicate[] predicates = criarRestricoes(escalaLouvorFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoEscalaLouvor> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(EscalaLouvorFilter escalaLouvorFilter, CriteriaBuilder builder,
			Root<EscalaLouvor> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		// semelhante a: where like %ssdafs%
		if (!StringUtils.isEmpty(escalaLouvorFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get("descricao")), "%" + escalaLouvorFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (escalaLouvorFilter.getDataDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get("dataDe"), escalaLouvorFilter.getDataDe()));
		}
		
		if (escalaLouvorFilter.getDataAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get("dataAte"), escalaLouvorFilter.getDataAte()));
		}
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRetricoesDePaginacao(TypedQuery<EscalaLouvor> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(EscalaLouvorFilter escalaLouvorFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<EscalaLouvor> root = criteria.from(EscalaLouvor.class);
		
		Predicate[] predicates = criarRestricoes(escalaLouvorFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
}
