package com.tessaro.sistema.service;

import java.time.LocalDate;
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
		Casamento casamento = buscarCasamentoPorId(id);
		return casamento;
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
	
	public void atualizarPropriedadeDataCasamento(Long id, LocalDate variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setDataCasamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeDataFechamentoDoCasamento(Long id, LocalDate variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setDataFechamentoDoCasamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeLocalCerimonia(Long id, String variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setLocalCerimonia(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeLocalRecepcao(Long id, String variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setLocalRecepcao(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeValorPacote(Long id, Double variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setValorPacote(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeValorPago(Long id, Double variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setValorPago(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeValorReceber(Long id, Double variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setValorReceber(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeFormaPagamento(Long id, String variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setFormaPagamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadePreCasamento(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setPreCasamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeMakingOfNoiva(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setMakingOfNoiva(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeMakingOfNoivo(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setMakingOfNoivo(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeFotolivro(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setFotolivro(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeQtdFotosCasamento(Long id, Integer variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setQtdFotosCasamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeQtdFotosPreCasamento(Long id, Integer variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setQtdFotosPreCasamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeCaixa(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setCaixa(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadePenDrive(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setPenDrive(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadePessoa(Long id, String cpf) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		Pessoa pessoaASalvar = buscarPessoaPeloCpf(cpf);
		pessoaASalvar.setCasamento(casamentoSalvo);
		casamentoSalvo.setPessoa(pessoaASalvar);
		repository.save(casamentoSalvo);
	}

	public void atualizarPropriedadeKitSogra(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setKitSogra(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeFotolivroPreCasamento(Long id, Boolean variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setFotolivroPreCasamento(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeTamanhoFotolivros(Long id, String variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setTamanhoFotolivros(variavel);
		repository.save(casamentoSalvo);
	}
	
	public void atualizarPropriedadeObservacoes(Long id, String variavel) {
		Casamento casamentoSalvo = buscarCasamentoPorId(id);
		casamentoSalvo.setObservacoes(variavel);
		repository.save(casamentoSalvo);
	}
	
	
/*------------------------------------*/
		/* METODOS ASSISTENTES*/
	
	public Pessoa buscarPessoaPeloCpf(String cpf) {
		cpf = CpfUtil.foramtarCpf(cpf);
		List<Pessoa> pessoaSalva = repositoryPessoa.findByCpf(cpf);
		Pessoa pessoa = null;
		if (!pessoaSalva.isEmpty()) {
			pessoa = pessoaSalva.get(0);
		} else {	
			throw new NaoExisteNaBaseException("Pessoa(CPF) informada nao existe na base de dados.");
		}
		return pessoa;
	}
	
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
														obj.isKitSogra(),
														obj.isFotolivroPreCasamento(),
														obj.getTamanhoFotolivros(),
														obj.getObservacoes(),
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
	
	private Casamento buscarCasamentoPorId(Long id) {
		Optional<Casamento> casamentoSalvo = repository.findById(id);
		Casamento casamento = null;
		if (!casamentoSalvo.isEmpty()) {
			casamento = casamentoSalvo.get();
		} else {
			throw new NaoExisteNaBaseException("Casamento informado não se encontra na base de dados");
		}
		return casamento;
	}
	
	
}
