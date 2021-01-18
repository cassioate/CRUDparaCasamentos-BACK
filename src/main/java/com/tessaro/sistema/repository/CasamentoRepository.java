package com.tessaro.sistema.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tessaro.sistema.model.Casamento;

@Repository
public interface CasamentoRepository extends JpaRepository<Casamento, Long>{
	
	@Query("select c from Casamento c join c.pessoa p where p.noiva = :noiva")
	List<Casamento> findByNome(@Param("noiva")String nome);
	
	@Query("select c from Casamento c where c.dataCasamento between :dataInicial and :dataFinal")
	List<Casamento> findByData(@Param("dataInicial")LocalDate dataInicial, @Param("dataFinal")LocalDate dataFinal);
	
	List<Casamento> findByDataCasamento(LocalDate dataCasamento);

}
	