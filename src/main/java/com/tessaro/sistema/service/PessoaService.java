package com.tessaro.sistema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tessaro.sistema.exceptionhandler.exceptions.AindaPossuiCasamento;
import com.tessaro.sistema.exceptionhandler.exceptions.CpfJaCadastrado;
import com.tessaro.sistema.exceptionhandler.exceptions.NaoExisteNaBaseException;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.PessoaDTO;
import com.tessaro.sistema.repository.PessoaRepository;
import com.tessaro.sistema.service.mapper.PessoaMapper;
import com.tessaro.sistema.util.CpfUtil;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> buscarTodos (){
		return repository.findAll();
	}
	
	public Pessoa buscarPorId (Long id){
		Optional<Pessoa> pessoa = repository.findById(id);
		return validarPessoaPorId(pessoa);
	}

	public PessoaDTO salvar (PessoaDTO pessoaDto){
		pessoaDto.setCpf(CpfUtil.foramtarCpf(pessoaDto.getCpf()));
		List<Pessoa> jaExiste = repository.findByCpf(pessoaDto.getCpf());
		validarNaoExistenciaNaBase(jaExiste);
		Pessoa pessoa = PessoaMapper.dtoToPessoa(pessoaDto);
		repository.save(pessoa);
		BeanUtils.copyProperties(pessoa, pessoaDto, "id", "casamento");
		return pessoaDto;
	}
	
	public void deletar (String cpf){
		cpf = CpfUtil.foramtarCpf(cpf);
		List<Pessoa> pessoa = repository.findByCpf(cpf);
		Pessoa pessoaSalva = validarPessoaParaDeletar(pessoa);
		repository.deleteById(pessoaSalva.getId());
	}
	
/*------------------------------------*/
		/* METODOS ASSISTENTES*/
	
	private Pessoa validarPessoaPorId(Optional<Pessoa> pessoa) {
		if (!pessoa.isEmpty()) {
			return pessoa.get();
		} else {
			throw new NaoExisteNaBaseException("Pessoa informada nao existe na base de dados.");
		}
	}
	
	private void validarNaoExistenciaNaBase(List<Pessoa> jaExiste) {
		if (!jaExiste.isEmpty()) {
			throw new CpfJaCadastrado();
		}
	}

	private Pessoa validarPessoaParaDeletar (List<Pessoa> pessoa) {
		Pessoa pessoaSalva = null;
		if (!pessoa.isEmpty()) {
			pessoaSalva = pessoa.get(0);
			if(pessoaSalva.getCasamento() != null) {
				throw new AindaPossuiCasamento("Usuario ainda possui associação de casamento!");
			}
		} else {
			throw new NaoExisteNaBaseException("Pessoa não pode ser deletada, pois não existe na base de dados.");
		}
		return pessoaSalva;
	}
	
}
