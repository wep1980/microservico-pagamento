package br.com.wepdev.microservicopagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -8544050223318576032L;

	
	public ResourceNotFoundException(String exception) {
		super(exception);
	}
}
