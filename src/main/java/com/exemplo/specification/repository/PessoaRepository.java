package com.exemplo.specification.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.exemplo.specification.entity.Pessoa;

@Repository
public interface PessoaRepository extends BaseRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

}
