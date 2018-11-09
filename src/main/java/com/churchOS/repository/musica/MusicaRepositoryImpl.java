package com.churchOS.repository.musica;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.churchOS.model.Musica;
import com.churchOS.repository.filter.MusicaFilter;

public class MusicaRepositoryImpl implements MusicaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Musica> filtrar(MusicaFilter musicaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Musica> criteria = builder.createQuery(Musica.class);
		Root<Musica> root = criteria.from(Musica.class);
		
		Predicate[] predicates = criarRestricoes(musicaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Musica> query = manager.createQuery(criteria);
		//adicionarRetricoesDePaginacao(query, pageable); 
		
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(MusicaFilter musicaFilter, CriteriaBuilder builder, Root<Musica> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(musicaFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get("descricao")), "%" + musicaFilter.getDescricao().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	/*private void adicionarRetricoesDePaginacao(TypedQuery<Musica> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(MusicaFilter musicaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Musica> root = criteria.from(Musica.class);
		
		Predicate[] predicates = criarRestricoes(musicaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}*/
	
}
