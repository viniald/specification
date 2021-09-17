package com.exemplo.specification.vo;

import com.exemplo.specification.entity.Pessoa;

import lombok.Data;

@Data
public class PessoaVO {
	
	
	private String nome;
	private String sobrenome;
	private Integer idade;
	private ContatoVO contatoVO;
	
	
	public PessoaVO(Pessoa pessoa) {
		if(pessoa == null) {
			return;
		}
		
		this.nome = pessoa.getNome();
		this.sobrenome = pessoa.getSobrenome();
		this.idade = pessoa.getIdade();
		this.contatoVO = new ContatoVO(pessoa.getContato());
	}
	
	public PessoaVO() {		
	}

}
