package com.tessaro.sistema.service.validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tessaro.sistema.model.Casamento;
import com.tessaro.sistema.model.dto.CasamentoDTO;
import com.tessaro.sistema.repository.CasamentoRepository;
import com.tessaro.sistema.service.annotations.DataValida;

public class DataValidator implements ConstraintValidator <DataValida, CasamentoDTO> {
	
	@Autowired 
	private CasamentoRepository repository;
	
	@Override
	public void initialize(DataValida a) {
 	}

	@Override
	public boolean isValid(CasamentoDTO casamento, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;
		List<Casamento> exist = null;
		exist = repository.findByDataCasamento(casamento.getDataCasamento());
		
				try {
					
					if (exist == null | exist.isEmpty()) {
						isValid = Boolean.TRUE; 
					} 
					
				} catch (Exception e) {
					// Continua retornando false
				}

		return isValid;
	}
}