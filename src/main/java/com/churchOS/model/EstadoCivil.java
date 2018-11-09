package com.churchOS.model;

public enum EstadoCivil {

	SOLTEIRO("Solteiro(a)"),
	CASADO("Casado(a)"),
	DIVORCIADO("Divorciado(a)"),
	VIUVO("Viúvo(a)");
	
	private String descricao;

	private EstadoCivil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
