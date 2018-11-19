package com.churchOS.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.churchOS.model.enums.TipoCulto;

@Entity
@Table(name = "escala_louvor")
public class EscalaLouvor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoCulto tipoCulto;
	
	private String descricao;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date data;
	
	@ManyToMany
	@JoinTable(name = "escala_ministro",  joinColumns = 
	@JoinColumn(name = "id_escala_louvor"), 
	inverseJoinColumns = @JoinColumn(name = "id_ministro"))
	private List<Ministro> ministros;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "escala_musicas",  joinColumns = 
	@JoinColumn(name = "id_escala_louvor"), 
	inverseJoinColumns = @JoinColumn(name = "id_musicas"))
	private List<Musica> musicas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoCulto getTipoCulto() {
		return tipoCulto;
	}

	public void setTipoCulto(TipoCulto tipoCulto) {
		this.tipoCulto = tipoCulto;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Ministro> getMinistros() {
		return ministros;
	}

	public void setMinistros(List<Ministro> ministros) {
		this.ministros = ministros;
	}

	public List<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(List<Musica> musicas) {
		this.musicas = musicas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EscalaLouvor other = (EscalaLouvor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
