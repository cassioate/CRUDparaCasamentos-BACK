package com.tessaro.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tessaro.sistema.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	List<Pessoa> findByCpf(String cpf);
	
	List<Pessoa> findByNoiva(String noiva);

}
