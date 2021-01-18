package com.tessaro.sistema.service.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tessaro.sistema.model.dto.PessoaDTO;
import com.tessaro.sistema.service.annotations.CpfValido;
import com.tessaro.sistema.util.CpfUtil;

public class CpfValidator implements ConstraintValidator<CpfValido, PessoaDTO> {
	
	@Override
	public void initialize(CpfValido a) {
 	}

	@Override
	public boolean isValid(PessoaDTO pessoa, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;
				try {
			
					if (CpfUtil.validarRegraCpf(pessoa.getCpf())){
						isValid = Boolean.TRUE;
					} 
						
				} catch (Exception e) {
					// Continua retornando false
				}
		return isValid;
	}

}