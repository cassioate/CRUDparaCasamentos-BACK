package com.tessaro.sistema.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tessaro.sistema.service.annotations.DataValida;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DataValida
public class CasamentoDTO {
	
	@NotNull
	private LocalDate dataCasamento;
	
	private LocalDate dataFechamentoDoCasamento;
	@Size(min = 1, max = 500)
	private String localCerimonia;
	private String localRecepcao;
	@NotNull
	private Double valorPacote;
	@NotNull
	private Double valorPago;
	private Double valorReceber;
	@NotNull
	private String formaPagamento;
	@NotNull
	private boolean preCasamento;
	@NotNull
	private boolean makingOfNoiva;
	@NotNull
	private boolean makingOfNoivo;
	@NotNull
	private boolean fotolivro;
	@NotNull
	private Integer qtdFotosCasamento;
	private Integer qtdFotosPreCasamento;
	@NotNull
	private boolean caixa;
	@NotNull
	private boolean penDrive;
	
	private PessoaDTO pessoa;

}
