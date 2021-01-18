package com.tessaro.sistema.util;

import org.springframework.beans.BeanUtils;

import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.EnderecoDTO;
import com.tessaro.sistema.model.dto.PessoaDTO;

public class PessoaUtils {

	public static PessoaDTO converterPessoaToDto(Pessoa pessoa) {
		PessoaDTO pessoaDto = new PessoaDTO();
		BeanUtils.copyProperties(pessoa, pessoaDto);
		EnderecoDTO enderecoDto = new EnderecoDTO();
		BeanUtils.copyProperties(pessoa.getEndereco(), enderecoDto);
		pessoaDto.setEndereco(enderecoDto);
		return pessoaDto;
	}
	
	public static String foramtarCpf(String cpf) {
		if (cpf.length() == 11) {
			String parte1 = cpf.substring(0, 3);
			String parte2 = cpf.substring(3, 6);
			String parte3 = cpf.substring(6, 9);
			String parte4 = cpf.substring(9, 11);
			String novoCpf = parte1 + "." + parte2 + "." + parte3 + "-" + parte4;
			return novoCpf;
		} else {
			return cpf;
		}
	}
}
