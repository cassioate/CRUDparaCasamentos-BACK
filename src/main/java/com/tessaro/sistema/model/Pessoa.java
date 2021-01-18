package com.tessaro.sistema.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String noiva;
	
	private String noivo;
	
	private String rg;
	
	@NotNull
	private String cpf;
	
	private String telefone;
	
	@Embedded
	private Endereco endereco;
		
	@JsonIgnore
	@OneToOne(mappedBy = "pessoa")
	private Casamento casamento;
	
	public Pessoa(String noiva, String noivo, String rg, String cpf, String telefone, Endereco endereco) {
		super();
		this.noiva = noiva;
		this.noivo = noivo;
		this.rg = rg;
		this.cpf = cpf;
		this.telefone = telefone;
		this.endereco = endereco;
	}
	
}
