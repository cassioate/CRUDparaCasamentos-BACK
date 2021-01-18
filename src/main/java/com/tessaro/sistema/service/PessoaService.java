package com.tessaro.sistema.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tessaro.sistema.exceptionhandler.CpfJaCadastrado;
import com.tessaro.sistema.model.Endereco;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.PessoaDTO;
import com.tessaro.sistema.repository.PessoaRepository;
import com.tessaro.sistema.util.PessoaUtils;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> buscarTodos (){
		return repository.findAll();
	}
	
	public Pessoa buscarPorId (Long id){
		Pessoa pessoa = repository.findById(id).get();
		if (pessoa != null) {
			return pessoa;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public PessoaDTO salvar (PessoaDTO pessoaDto){
		pessoaDto.setCpf(PessoaUtils.foramtarCpf(pessoaDto.getCpf()));
		List<Pessoa> jaExiste = repository.findByCpf(pessoaDto.getCpf());
		
		if (!jaExiste.isEmpty()) {
			throw new CpfJaCadastrado();
		}
		
		Pessoa pessoa = new Pessoa();
		BeanUtils.copyProperties(pessoaDto, pessoa);
		
		Endereco endereco = new Endereco();
		BeanUtils.copyProperties(pessoaDto.getEndereco(), endereco);
		
		pessoa.setEndereco(endereco);
		
		repository.save(pessoa);
		BeanUtils.copyProperties(pessoa, pessoaDto, "id", "casamento");
		return pessoaDto;
	}

	public void deletar (String cpf){
		List<Pessoa> pessoa = repository.findByCpf(cpf);
		Pessoa pessoaSalva = null;
		if (!pessoa.isEmpty()) {
			pessoaSalva = pessoa.get(0);
		} else {
			throw new NoSuchElementException();
		}
		repository.deleteById(pessoaSalva.getId());
	}

}
