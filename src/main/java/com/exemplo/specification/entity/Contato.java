package com.exemplo.specification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "contato", schema="cadastro")
public class Contato {
	
	@Id
	@Column(name = "id")
	private Long id;	
	
	@Column(name = "celular")
	private String numeroCelular;
	
	
	@Column(name = "telefone")
	private String numeroTelefone;
	
	
	@Column(name = "email")
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim")
	private Date dataFim;

}
