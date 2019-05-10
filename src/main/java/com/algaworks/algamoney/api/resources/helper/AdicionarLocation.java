package com.algaworks.algamoney.api.resources.helper;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class AdicionarLocation {

	public static URI location(Long codigo) {
		
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{codigo}")
					.buildAndExpand(codigo)
					.toUri();
		
		return uri;
	}
	
}
