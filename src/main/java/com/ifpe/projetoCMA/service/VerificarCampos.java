package com.ifpe.projetoCMA.service;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ifpe.projetoCMA.exception.verificacaoCamposNulosException;

public class VerificarCampos {

	public static boolean verificarTemCamposNulos(Object obj) {

		String cla = "";

		if (obj == null)
			return true; // primeiro verifica se o obj é nulo

		Class<?> classe = obj.getClass(); // depois coleta a classe do objeto

		cla = classe.toString();

		Field[] campos = classe.getDeclaredFields(); // coleta os campos do objeto

		if (campos.length == 0)
			return true; // se o objeto não tiver campos retorna true para precaver que um objeto sem
							// campos seja confirmado como "objeto sem campos nulos";
		try {
			for (Field campo : campos) {
				campo.setAccessible(true); // permitir acesso a atributos privados
				if (campo.get(obj) == null) // acessar o conteudo da variavel e comparar praver se é null.
					return true;

			}

		} catch (Exception e) {
			throw new verificacaoCamposNulosException(cla); // coletando a classe do obj que gerol o erro.
		}
		return false;

	}

	public static boolean validarEmail(String email) {
		if(email == null || email.isBlank()) return false;
		
	    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

    public static boolean validarSenha(String senha) {
    	if(senha == null || senha.isBlank()) return false;
    	
        String regex = "^(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(senha);
        return matcher.matches();
    }
}