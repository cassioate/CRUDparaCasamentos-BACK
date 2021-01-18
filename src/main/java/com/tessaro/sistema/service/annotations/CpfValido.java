package com.tessaro.sistema.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tessaro.sistema.service.validators.CpfValidator;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CpfValidator.class})
public @interface CpfValido {
	
	String message() default "CPF deve estar no seguinte formato: '11122233344' e deve ser um valor de CPF valido!";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
    
}
