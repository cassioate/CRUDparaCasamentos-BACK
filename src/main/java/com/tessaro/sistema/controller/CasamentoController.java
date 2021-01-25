package com.tessaro.sistema.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tessaro.sistema.model.Casamento;
import com.tessaro.sistema.model.dto.CasamentoDTO;
import com.tessaro.sistema.service.CasamentoService;
import com.tessaro.sistema.service.mapper.PessoaMapper;

@RestController
@RequestMapping("/casamentos")
public class CasamentoController {

	@Autowired
	private CasamentoService service;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Casamento>> buscarCasamentos (){
		List<Casamento> casamentos = service.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(casamentos);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Casamento> buscarUmCasamento (@PathVariable Long id){
		Casamento casamento = service.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(casamento);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/noivas")
	public ResponseEntity<List<Casamento>> buscarCasamentosPorNome (@RequestParam(required = false) String nome){
		List<Casamento> casamento = service.buscarClienteNome(nome);
		return ResponseEntity.status(HttpStatus.OK).body(casamento);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CasamentoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="3") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="dataCasamento") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Casamento> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CasamentoDTO> listDto = 
				list.map(
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
						PessoaMapper.pessoaToDto(obj.getPessoa())));  
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/filtro-publicacao")
	public ResponseEntity<List<CasamentoDTO>> findPublicacao(
			@RequestParam(value="dataInicial") String dataInicial, 
			@RequestParam(value="dataFinal") String dataFinal){
		return new ResponseEntity<List<CasamentoDTO>>(service.findCasamentoPorData(dataInicial, dataFinal), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CasamentoDTO> salvarCasamento (@Valid @RequestBody CasamentoDTO casamento, @RequestParam String cpfNoiva){
		CasamentoDTO casamentoSalva = service.salvar(casamento, cpfNoiva);
		return ResponseEntity.status(HttpStatus.OK).body(casamentoSalva);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{cpf}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable String cpf) {
		service.deletar(cpf);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/dataCasamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarDataCasamento(@PathVariable Long id, @RequestBody LocalDate variavel){
		service.atualizarPropriedadeDataCasamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/dataFechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarDataFechamentoDoCasamento(@PathVariable Long id, @RequestBody LocalDate variavel){
		service.atualizarPropriedadeDataFechamentoDoCasamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/localCerimonia")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarLocalCerimonia(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeLocalCerimonia (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/localRecepcao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarLocalRecepcao(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeLocalRecepcao (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/valorPacote")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarValorPacote(@PathVariable Long id, @RequestBody Double variavel){
		service.atualizarPropriedadeValorPacote (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/valorPago")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarValorPago(@PathVariable Long id, @RequestBody Double variavel){
		service.atualizarPropriedadeValorPago (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/valorReceber")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarValorReceber(@PathVariable Long id, @RequestBody Double variavel){
		service.atualizarPropriedadeValorReceber (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/formaPagamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarFormaPagamento(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeFormaPagamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/preCasamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPreCasamento(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadePreCasamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/makingOfNoiva")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarMakingOfNoiva(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeMakingOfNoiva (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/makingOfNoivo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarMakingOfNoivo(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeMakingOfNoivo (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/fotolivro")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarFotolivro(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeFotolivro (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/qtdFotosCasamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarQtdFotosCasamento(@PathVariable Long id, @RequestBody Integer variavel){
		service.atualizarPropriedadeQtdFotosCasamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/qtdFotosPreCasamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarQtdFotosPreCasamento(@PathVariable Long id, @RequestBody Integer variavel){
		service.atualizarPropriedadeQtdFotosPreCasamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/caixa")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCaixa(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeCaixa (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/penDrive")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPenDrive(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadePenDrive (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/pessoa")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPessoa(@PathVariable Long id, @RequestBody String cpf){
		service.atualizarPropriedadePessoa (id, cpf);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/kitSogra")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarKitSogra(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeKitSogra (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/fotolivroPreCasamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarFotolivroPreCasamento(@PathVariable Long id, @RequestBody Boolean variavel){
		service.atualizarPropriedadeFotolivroPreCasamento (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/tamanhoFotolivros")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarTamanhoFotolivros(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeTamanhoFotolivros (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/observacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarObservacoes(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeObservacoes (id, variavel);
	}
	
}
