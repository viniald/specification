package com.exemplo.specification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.specification.entity.Pessoa;
import com.exemplo.specification.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	PessoaService pessoaService;

	@GetMapping(path = "/consulta/{ordenacao}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultaCliente(Pessoa pessoa, @PathVariable(name = "ordenacao") String ordenacao)
			throws Exception {
		return new ResponseEntity<>(pessoaService.consultar(pessoa, ordenacao, null), HttpStatus.OK);
	}

	@GetMapping(path = "/consulta-paginada/{ordenacao}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultaClientePaginado(Pessoa pessoa,
			@PathVariable(name = "ordenacao") String ordenacao, @PathVariable(name = "pagina") Integer pagina)
			throws Exception {
		return new ResponseEntity<>(pessoaService.consultar(pessoa, ordenacao, pagina), HttpStatus.OK);
	}

}
