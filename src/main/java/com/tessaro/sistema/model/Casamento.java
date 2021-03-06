package com.tessaro.sistema.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Casamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private LocalDate dataCasamento;
	
	private LocalDate dataFechamentoDoCasamento;
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
	private boolean fotolivroPreCasamento;
	@NotNull
	private boolean makingOfNoiva;
	@NotNull
	private boolean makingOfNoivo;
	@NotNull
	private boolean fotolivro;
	@NotNull
	private boolean kitSogra;
	@NotNull
	private Integer qtdFotosCasamento;
	private Integer qtdFotosPreCasamento;
	@NotNull
	private boolean caixa;
	@NotNull
	private boolean penDrive;
	@NotNull
	private String tamanhoFotolivros;
	
	private String observacoes;
	
	@OneToOne	
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	public Casamento(Long id, LocalDate dataCasamento, LocalDate dataFechamentoDoCasamento,
			String localCerimonia, String localRecepcao, Double valorPacote, Double valorPago,
			Double valorReceber, String formaPagamento, boolean preCasamento,
			boolean fotolivroPreCasamento, boolean makingOfNoiva, boolean makingOfNoivo,
			boolean fotolivro, boolean kitSogra, Integer qtdFotosCasamento,
			Integer qtdFotosPreCasamento, boolean caixa, boolean penDrive,
			String tamanhoFotolivros, String observacoes) {
		super();
		this.id = id;
		this.dataCasamento = dataCasamento;
		this.dataFechamentoDoCasamento = dataFechamentoDoCasamento;
		this.localCerimonia = localCerimonia;
		this.localRecepcao = localRecepcao;
		this.valorPacote = valorPacote;
		this.valorPago = valorPago;
		this.valorReceber = valorReceber;
		this.formaPagamento = formaPagamento;
		this.preCasamento = preCasamento;
		this.fotolivroPreCasamento = fotolivroPreCasamento;
		this.makingOfNoiva = makingOfNoiva;
		this.makingOfNoivo = makingOfNoivo;
		this.fotolivro = fotolivro;
		this.kitSogra = kitSogra;
		this.qtdFotosCasamento = qtdFotosCasamento;
		this.qtdFotosPreCasamento = qtdFotosPreCasamento;
		this.caixa = caixa;
		this.penDrive = penDrive;
		this.tamanhoFotolivros = tamanhoFotolivros;
		this.observacoes = observacoes;
	}

}
