package com.tessaro.sistema.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tessaro.sistema.model.Endereco;
import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.PessoaDTO;
import com.tessaro.sistema.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService service;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Pessoa>> buscarPessoas (){
		List<Pessoa> pessoas = service.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(pessoas);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarUmaPessoa (@PathVariable Long id){
		Pessoa pessoa = service.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(pessoa);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PessoaDTO> salvarPessoa (@Valid @RequestBody PessoaDTO pessoa){
		PessoaDTO pessoaSalva = service.salvar(pessoa);
		return ResponseEntity.status(HttpStatus.OK).body(pessoaSalva);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{cpf}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable String cpf) {
		service.deletar(cpf);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/noiva")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarNoiva(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeNoiva (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/noivo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarNoivo(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeNoivo (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/rg")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarRg(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeRg (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/cpf")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCpf (@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeCpf (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/telefone")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarTelefone(@PathVariable Long id, @RequestBody String variavel){
		service.atualizarPropriedadeTelefone (id, variavel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/endereco")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarEndereco(@PathVariable Long id, @RequestBody Endereco variavel){
		service.atualizarPropriedadeEndereco(id, variavel);
	}
	
}
