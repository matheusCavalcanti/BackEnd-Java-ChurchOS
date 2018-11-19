package com.churchOS.repository.projection;

import java.util.Calendar;

import com.churchOS.model.enums.TipoCulto;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ResumoEscalaLouvor {

	private Long id;
	private String descricao;
	private TipoCulto tipoCulto;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Calendar data;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public TipoCulto getTipoCulto() {
		return tipoCulto;
	}
	public void setTipoCulto(TipoCulto tipoCulto) {
		this.tipoCulto = tipoCulto;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public ResumoEscalaLouvor(Long id, String descricao, TipoCulto tipoCulto, Calendar data) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.tipoCulto = tipoCulto;
		this.data = data;
	}
	
}