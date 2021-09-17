package com.exemplo.specification.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.exemplo.specification.entity.Contato;
import com.exemplo.specification.entity.Pessoa;
import com.exemplo.specification.repository.PessoaRepository;
import com.exemplo.specification.util.EnumOrdenacaoConsultaPessoa;
import com.exemplo.specification.vo.PessoaVO;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	public Object[] consultar(Pessoa pessoa, String ordenacao, Integer pagina) {

		List<Pessoa> pessoas = null;
		long tamanho = 0;
		int pessoasPorPagina = 5;

		List<PessoaVO> listaPessoasVO = new ArrayList<PessoaVO>();

		if (pagina != null) {
			PageRequest pageRequest = PageRequest.of(pagina - 1, pessoasPorPagina, Sort.unsorted());

			Specification<Pessoa> specification = montaConsulta(pessoa, ordenacao);

			Page<Pessoa> pagePessoas = pessoaRepository.findAll(specification, pageRequest);

			pessoas = pagePessoas.toList();

			tamanho = pagePessoas.getNumberOfElements();

		} else {
			pessoas = pessoaRepository.findAll(montaConsulta(pessoa, ordenacao));
			tamanho = pessoas.size();
		}

		for (Pessoa p : pessoas) {
			listaPessoasVO.add(new PessoaVO(p));
		}
		return new Object[] { tamanho, listaPessoasVO };
	}

	private Specification<Pessoa> montaConsulta(Pessoa pessoa, String ordenacao) {

		return new Specification<Pessoa>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Pessoa> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				List<Order> orders = new ArrayList<Order>();

				// Ordenacao
				EnumOrdenacaoConsultaPessoa enumOrdenacaoConsultaPessoa = null;
				try {
					enumOrdenacaoConsultaPessoa = EnumOrdenacaoConsultaPessoa.valueOfDescricao(ordenacao);
				} catch (Exception e) {
					enumOrdenacaoConsultaPessoa = EnumOrdenacaoConsultaPessoa.NOME;
				}

				if (enumOrdenacaoConsultaPessoa.equals(EnumOrdenacaoConsultaPessoa.NOME)) {
					orders.add(builder.asc(root.get("nome")));
				}

				if (enumOrdenacaoConsultaPessoa.equals(EnumOrdenacaoConsultaPessoa.SOBRENOME)) {
					orders.add(builder.asc(root.get("sobrenome")));
				}

				if (enumOrdenacaoConsultaPessoa.equals(EnumOrdenacaoConsultaPessoa.IDADE)) {
					orders.add(builder.asc(root.get("idade")));
				}

				// Clausulas

				// to lower case e like
				if (StringUtils.isNotEmpty(pessoa.getNome())) {
					predicates.add(builder.like(builder.lower(root.<String>get("nome")), "%" + pessoa.getNome().toLowerCase() + "%"));
				}

				// equal
				if (StringUtils.isNotEmpty(pessoa.getSobrenome())) {
					predicates.add(builder.equal(root.<String>get("sobrenome"), pessoa.getSobrenome()));
				}

				if (pessoa.getIdade() != null) {
					predicates.add(builder.equal(root.<Integer>get("idade"), pessoa.getIdade()));
				}

				// Join Contato

				if (pessoa.getContato() != null) {

					if (StringUtils.isNotEmpty(pessoa.getContato().getNumeroCelular()) && StringUtils.isNotEmpty(pessoa.getContato().getNumeroTelefone()) && StringUtils.isNotEmpty(pessoa.getContato().getEmail())) {

						Join<Pessoa, Contato> joinPessoaContato = root.join("contato");

						if (StringUtils.isNotEmpty(pessoa.getContato().getNumeroCelular())) {
							try {
								predicates.add(builder.equal(joinPessoaContato.<String>get("numeroCelular"), pessoa.getContato().getNumeroCelular()));
							} catch (Exception e) {
							}
						}

						// like
						if (StringUtils.isNotEmpty(pessoa.getContato().getNumeroTelefone())) {
							try {
								predicates.add(builder.like(joinPessoaContato.<String>get("numeroTelefone"), "%" + pessoa.getContato().getNumeroTelefone() + "%"));
							} catch (Exception e) {
							}
						}

						if (StringUtils.isNotEmpty(pessoa.getContato().getEmail())) {
							try {
								predicates.add(builder.equal(joinPessoaContato.<String>get("email"), pessoa.getContato().getEmail()));
							} catch (Exception e) {
							}
						}

					}

				}

				// data fim null
				predicates.add(builder.isNull(root.<Date>get("dataFim")));

				query.orderBy(orders);

				return (builder.and(predicates.toArray(new Predicate[] {})));
			}

		};

	}
}
