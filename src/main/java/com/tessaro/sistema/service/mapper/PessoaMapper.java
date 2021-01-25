package com.tessaro.sistema.service.mapper;

import org.springframework.beans.BeanUtils;

import com.tessaro.sistema.model.Endereco;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.EnderecoDTO;
import com.tessaro.sistema.model.dto.PessoaDTO;

public class PessoaMapper {
	
	public static PessoaDTO pessoaToDto(Pessoa pessoa) {
		PessoaDTO pessoaDto = new PessoaDTO();
		BeanUtils.copyProperties(pessoa, pessoaDto);
		EnderecoDTO enderecoDto = new EnderecoDTO();
		BeanUtils.copyProperties(pessoa.getEndereco(), enderecoDto);
		pessoaDto.setEndereco(enderecoDto);
		return pessoaDto;
	}
	
	public static Pessoa dtoToPessoa(PessoaDTO pessoaDto) {
		Pessoa pessoa = new Pessoa();
		BeanUtils.copyProperties(pessoaDto, pessoa, "id");
		Endereco endereco = new Endereco();
		BeanUtils.copyProperties(pessoaDto.getEndereco(), endereco);
		pessoa.setEndereco(endereco);
		return pessoa;
	}

}
