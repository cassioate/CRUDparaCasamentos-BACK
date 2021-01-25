package com.tessaro.sistema.service.mapper;

import org.springframework.beans.BeanUtils;

import com.tessaro.sistema.model.Casamento;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.CasamentoDTO;
import com.tessaro.sistema.model.dto.EnderecoDTO;
import com.tessaro.sistema.model.dto.PessoaDTO;

public class CasamentoMapper {
	
	
	public static Casamento dtoToCasamento (CasamentoDTO casamentoDto, Pessoa pessoa) {
		Casamento casamento = new Casamento();
		BeanUtils.copyProperties(casamentoDto, casamento);
		pessoa.setCasamento(casamento);
		casamento.setPessoa(pessoa);
		casamento.setValorReceber(casamento.getValorPacote() - casamento.getValorPago());
		
		return casamento;
	}
	
	public static CasamentoDTO casamentoToDto (Casamento casamento) {
		CasamentoDTO casamentoDto = new CasamentoDTO();
		BeanUtils.copyProperties(casamento, casamentoDto, "id");
		
		EnderecoDTO endereco = new EnderecoDTO();
		BeanUtils.copyProperties(casamento.getPessoa().getEndereco(), endereco);
		
		PessoaDTO pessoaDto = new PessoaDTO();
		BeanUtils.copyProperties(casamento.getPessoa(), pessoaDto, "id");
		
		pessoaDto.setEndereco(endereco);
		casamentoDto.setPessoa(pessoaDto);
		return casamentoDto;
	}
	
}
