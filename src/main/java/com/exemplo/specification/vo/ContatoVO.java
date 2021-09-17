package com.exemplo.specification.vo;

import com.exemplo.specification.entity.Contato;

import lombok.Data;

@Data
public class ContatoVO {
	
	private String celular;
	private String telefone;
	private String email;
	
	
	public ContatoVO(Contato contato) {
		if(contato ==null) {
			return;
		}
		
		this.celular = contato.getNumeroCelular();
		this.telefone = contato.getNumeroTelefone();
		this.email = contato.getEmail();
	}
	
	public ContatoVO() {
		
	}

}
