 package com.churchOS.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class EscalaLouvorFilter {

	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataAte;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataDe() {
		return dataDe;
	}
	public void setDataDe(LocalDate dataDe) {
		this.dataDe = dataDe;
	}
	public LocalDate getDataAte() {
		return dataAte;
	}
	public void setDataAte(LocalDate dataAte) {
		this.dataAte = dataAte;
	}
	
}
