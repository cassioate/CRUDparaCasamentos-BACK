package com.tessaro.sistema.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tessaro.sistema.service.validators.DataValidator;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DataValidator.class})
public @interface DataValida {
	
	String message() default "Já existe cliente com casamento marcado nessa data.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};

}
