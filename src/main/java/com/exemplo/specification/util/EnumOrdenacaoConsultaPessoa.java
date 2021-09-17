package com.exemplo.specification.util;

public enum EnumOrdenacaoConsultaPessoa {	
	
	IDADE("idade"),
	NOME("nome"),
	SOBRENOME("sobrenome");
	
	private final String descricao;
	
	private EnumOrdenacaoConsultaPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static EnumOrdenacaoConsultaPessoa valueOfDescricao(String descricao) throws Exception {
		for (EnumOrdenacaoConsultaPessoa enumOrdenacaoConsultaPessoa : values()) {
			if (enumOrdenacaoConsultaPessoa.getDescricao().equals(descricao)) {
				return enumOrdenacaoConsultaPessoa;
			}
		}
		throw new Exception("Nenhuma Ordenação para Consultar Fechamento encontrado para a descrição " + descricao);
	}

}
