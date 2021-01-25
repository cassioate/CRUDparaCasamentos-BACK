package com.tessaro.sistema.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping
	public ResponseEntity<List<Casamento>> buscarCasamentos (){
		List<Casamento> casamentos = service.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(casamentos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Casamento> buscarUmCasamento (@PathVariable Long id){
		Casamento casamento = service.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(casamento);
	}
	
	@GetMapping("/noivas")
	public ResponseEntity<List<Casamento>> buscarCasamentosPorNome (@RequestParam(required = false) String nome){
		List<Casamento> casamento = service.buscarClienteNome(nome);
		return ResponseEntity.status(HttpStatus.OK).body(casamento);
	}
	
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
						PessoaMapper.pessoaToDto(obj.getPessoa())));  
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/filtro-publicacao")
	public ResponseEntity<List<CasamentoDTO>> findPublicacao(
			@RequestParam(value="dataInicial") String dataInicial, 
			@RequestParam(value="dataFinal") String dataFinal){
		return new ResponseEntity<List<CasamentoDTO>>(service.findCasamentoPorData(dataInicial, dataFinal), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CasamentoDTO> salvarCasamento (@Valid @RequestBody CasamentoDTO casamento, @RequestParam String cpfNoiva){
		
		CasamentoDTO casamentoSalva = service.salvar(casamento, cpfNoiva);
		return ResponseEntity.status(HttpStatus.OK).body(casamentoSalva);
	}

	@DeleteMapping("/{cpf}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable String cpf) {
		service.deletar(cpf);
	}
	
}
