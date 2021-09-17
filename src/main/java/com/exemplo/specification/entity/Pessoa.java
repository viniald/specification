package com.exemplo.specification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "pessoa", schema="cadastro")
public class Pessoa {
	
	@Id
	@Column(name = "id")
	private Long id;	

	@Column(name = "nome")
	private String nome;	
	
	@Column(name = "sobrenome")
	private String sobrenome;	
	
	@Column(name = "idade")
	private Integer idade;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_contato")
	private Contato contato;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim")
	private Date dataFim;

}
