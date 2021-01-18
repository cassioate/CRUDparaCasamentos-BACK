package com.tessaro.sistema.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tessaro.sistema.exceptionhandler.CpfNaoExiste;
import com.tessaro.sistema.exceptionhandler.NegocioException;
import com.tessaro.sistema.model.Casamento;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.CasamentoDTO;
import com.tessaro.sistema.model.dto.EnderecoDTO;
import com.tessaro.sistema.model.dto.PessoaDTO;
import com.tessaro.sistema.repository.CasamentoRepository;
import com.tessaro.sistema.repository.PessoaRepository;
import com.tessaro.sistema.util.PessoaUtils;
import com.tessaro.sistema.util.TimeUtil;

@Service
public class CasamentoService {
	
	@Autowired
	private CasamentoRepository repository;
	
	@Autowired
	private PessoaRepository repositoryPessoa;

	public List<Casamento> buscarTodos (){
		return repository.findAll();
	}
	
	public Casamento buscarPorId (Long id){
		Casamento casamento = repository.findById(id).get();
		if (casamento != null) {
			return casamento;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public List<Casamento> buscarClienteNome(String nome) {	
		List<Casamento> casamento = repository.findByNome(nome);
		if (!casamento.isEmpty()) {
			return casamento;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public CasamentoDTO salvar (CasamentoDTO casamentoDto, String cpf){
		List<Pessoa> listNoivaEncontrada = repositoryPessoa.findByCpf(cpf);
		Pessoa noivaEncontrada = null;
		Casamento casamento = new Casamento();
		
		if (!listNoivaEncontrada.isEmpty()) {
			noivaEncontrada = listNoivaEncontrada.get(0);
			BeanUtils.copyProperties(casamentoDto, casamento);
			if(noivaEncontrada.getCasamento() != null) {
				throw new NegocioException();
			}
		} else {
			throw new CpfNaoExiste();
		}
		
		casamento.setPessoa(noivaEncontrada);
		noivaEncontrada.setCasamento(casamento);
		casamento.setValorReceber(casamento.getValorPacote() - casamento.getValorPago());
		
		Casamento casamentoSalva = repository.save(casamento);
		BeanUtils.copyProperties(casamentoSalva, casamentoDto, "id");
		
		EnderecoDTO endereco = new EnderecoDTO();
		BeanUtils.copyProperties(casamentoSalva.getPessoa().getEndereco(), endereco);
		
		PessoaDTO pessoaDto = new PessoaDTO();
		BeanUtils.copyProperties(noivaEncontrada, pessoaDto, "id");
		
		pessoaDto.setEndereco(endereco);
		casamentoDto.setPessoa(pessoaDto);
		
		return casamentoDto;
	}
	
	public void deletar (String cpf){
		List<Pessoa> pessoa = repositoryPessoa.findByCpf(cpf);
		Pessoa pessoaSalva = null;
		if (!pessoa.isEmpty()) {
			pessoaSalva = pessoa.get(0);
		} else {
			throw new NoSuchElementException();
		}
		repository.delete(pessoaSalva.getCasamento());
	}

	public Page<Casamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public List<CasamentoDTO> findCasamentoPorData(String dataInicial, String dataFinal) {
		List<CasamentoDTO> visualizar = new ArrayList<>();
		List<Casamento> list = repository.findByData(TimeUtil.toLocalDate(dataInicial),TimeUtil.toLocalDate(dataFinal));
		
		visualizar = list.stream().map(
						obj -> new CasamentoDTO(
						obj.getDataCasamento(),
						obj.getDataFechamentoDoCasamento(),
						obj.getLocalCerimonia(),
						obj.getLocalRecepcao(),
						obj.getValorPacote(),
						obj.getValorPago(),
						obj.getValorReceber(),
						obj.getFormaPagamento(),
						obj.isPreCasamento(),
						obj.isMakingOfNoiva(),
						obj.isMakingOfNoivo(),
						obj.isFotolivro(),
						obj.getQtdFotosCasamento(),
						obj.getQtdFotosPreCasamento(),
						obj.isCaixa(),
						obj.isPenDrive(),
						PessoaUtils.converterPessoaToDto(obj.getPessoa())
						))
				.collect(Collectors.toList());
		
		if (visualizar.isEmpty()) {
			throw new NoSuchElementException();
		}

		return visualizar;
	}
	
}
