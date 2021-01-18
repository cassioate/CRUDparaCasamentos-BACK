package com.tessaro.sistema.model.dto;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

import com.tessaro.sistema.service.annotations.CpfValido;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@CpfValido
public class PessoaDTO {
	
	@NotNull
	private String noiva;
	
	private String noivo;
	
	private String rg;
	
	@NotNull
	private String cpf;
	
	@NotNull
	private String telefone;
	
	@Embedded
	private EnderecoDTO endereco;
		
}