package com.ifpe.projetoCMA.service;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class ExtratorId {

	public static Long extrair(JwtAuthenticationToken token) {
		long userId = Long.parseLong(token.getName());
		return userId;
	}
}
