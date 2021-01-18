package com.tessaro.sistema.service.validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tessaro.sistema.config.usuario.User;
import com.tessaro.sistema.config.usuario.UserRepository;
import com.tessaro.sistema.config.usuario.DTO.UserDTO;
import com.tessaro.sistema.service.annotations.UserValido;

public class UserValidator implements ConstraintValidator <UserValido, UserDTO> {
	
	@Autowired 
	private UserRepository repository;
	
	@Override
	public void initialize(UserValido a) {
 	}

	@Override
	public boolean isValid(UserDTO usuario, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;
		List<User> exist = null;
		exist = repository.findByEmail(usuario.getEmail());
		
				try {
					if(usuario.getEmail() == null ) {
						isValid = Boolean.TRUE; 
					}
					
					if (exist == null | exist.isEmpty()) {
						isValid = Boolean.TRUE; 
					} 
					
					if (usuario.getEmail().isBlank()) {
						isValid = Boolean.TRUE; 
						}
					
				} catch (Exception e) {
					// Continua retornando false
				}

		return isValid;
	}
}