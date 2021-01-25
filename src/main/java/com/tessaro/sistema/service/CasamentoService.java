package com.tessaro.sistema.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tessaro.sistema.exceptionhandler.exceptions.CpfNaoExiste;
import com.tessaro.sistema.exceptionhandler.exceptions.JaPossuiCasamentoException;
import com.tessaro.sistema.exceptionhandler.exceptions.NaoExisteNaBaseException;
import com.tessaro.sistema.model.Casamento;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.CasamentoDTO;
import com.tessaro.sistema.repository.CasamentoRepository;
import com.tessaro.sistema.repository.PessoaRepository;
import com.tessaro.sistema.service.mapper.CasamentoMapper;
import com.tessaro.sistema.service.mapper.PessoaMapper;
import com.tessaro.sistema.util.CpfUtil;
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
		Optional<Casamento> casamento = repository.findById(id);
		return validandoCasamentoPorId(casamento);
	}

	public List<Casamento> buscarClienteNome(String nome) {	
		List<Casamento> casamento = repository.findByNome(nome);
		return validandoCasamentoEmLista(casamento);
	}

	public CasamentoDTO salvar (CasamentoDTO casamentoDto, String cpf){
		cpf = CpfUtil.foramtarCpf(cpf);
		List<Pessoa> listNoivaEncontrada = repositoryPessoa.findByCpf(cpf);
		Pessoa noivaEncontrada = validandoPessoaEmLista(listNoivaEncontrada);
		Casamento casamento = repository.save(CasamentoMapper.dtoToCasamento(casamentoDto, noivaEncontrada));
		casamentoDto = CasamentoMapper.casamentoToDto(casamento);
		return casamentoDto;
	}

	public void deletar (String cpf){
		List<Pessoa> pessoa = repositoryPessoa.findByCpf(cpf);
		Pessoa pessoaSalva = validandoPessoaParaDeletar(pessoa);
		repository.delete(pessoaSalva.getCasamento());
	}

	public Page<Casamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public List<CasamentoDTO> findCasamentoPorData(String dataInicial, String dataFinal) {
		List<Casamento> list = repository.findByData(TimeUtil.toLocalDate(dataInicial),TimeUtil.toLocalDate(dataFinal));
		List<CasamentoDTO> visualizar = validandoListaDeCasamentosDto(list);
		return visualizar;
	}
	
	
/*------------------------------------*/
		/* METODOS ASSISTENTES*/
	
	private List<CasamentoDTO> validandoListaDeCasamentosDto(List<Casamento> list) {
		List<CasamentoDTO> casamentosDto = list.stream().map(
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
														PessoaMapper.pessoaToDto(obj.getPessoa())
														))
												.collect(Collectors.toList());
	
		if (casamentosDto.isEmpty()) {
		throw new NaoExisteNaBaseException("Não existem dados entre as datas informadas.");
		}
			return casamentosDto;
	}

	public Pessoa validandoPessoaParaDeletar (List<Pessoa> pessoa) {
		Pessoa pessoaSalva = null;
		if (!pessoa.isEmpty()) {
			pessoaSalva = pessoa.get(0);
		} else {
			throw new NaoExisteNaBaseException("Pessoa a ser deletada não existe na base de dados.");
		}
		return pessoaSalva;
	}
	
	private Pessoa validandoPessoaEmLista(List<Pessoa> listNoivaEncontrada) {
		Pessoa pessoa = null;
		if (!listNoivaEncontrada.isEmpty()) {
			pessoa = listNoivaEncontrada.get(0);
			if(pessoa.getCasamento() != null) {
				throw new JaPossuiCasamentoException("Pessoa informada já possui casamento");
			}
		} else {
			throw new CpfNaoExiste();
		}
		return pessoa;
	}
	
	private List<Casamento> validandoCasamentoEmLista(List<Casamento> casamento) {
		if (!casamento.isEmpty()) {
			return casamento;
		} else {
			throw new NaoExisteNaBaseException("Não existem casamentos com o nome de cliente informado.");
		}
	}
	
	private Casamento validandoCasamentoPorId(Optional<Casamento> casamento) {
		if (!casamento.isEmpty()) {
			return casamento.get();
		} else {
			throw new NaoExisteNaBaseException("Casamento informado não se encontra na base de dados");
		}
	}
	
}
