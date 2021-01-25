package com.tessaro.sistema.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tessaro.sistema.model.Pessoa;
import com.tessaro.sistema.model.dto.PessoaDTO;
import com.tessaro.sistema.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService service;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> buscarPessoas (){
		List<Pessoa> pessoas = service.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(pessoas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarUmaPessoa (@PathVariable Long id){
		Pessoa pessoa = service.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(pessoa);
	}
	
	@PostMapping
	public ResponseEntity<PessoaDTO> salvarPessoa (@Valid @RequestBody PessoaDTO pessoa){
		PessoaDTO pessoaSalva = service.salvar(pessoa);
		return ResponseEntity.status(HttpStatus.OK).body(pessoaSalva);
	}
	
	@DeleteMapping("/{cpf}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable String cpf) {
		service.deletar(cpf);
	}
}
