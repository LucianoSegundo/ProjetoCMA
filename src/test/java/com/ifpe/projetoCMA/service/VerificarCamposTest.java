package com.ifpe.projetoCMA.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ifpe.projetoCMA.exception.resposta400.verificacaoCamposNulosException;

@SpringBootTest
@ActiveProfiles("test")
class VerificarCamposTest {

    // Teste para verificarCamposNulos
	
    @Test
	@DisplayName("Verificar Objeto Null")
    void verificarObjetoNull() {
        assertTrue(VerificarCampos.verificarTemCamposNulos(null), "Deve retornar true para objeto null");
    }

    @Test
	@DisplayName("Verificar Campos Nulos")
    void verificarCamposNulos() {
        ObjetoDeTeste obj = new ObjetoDeTeste(null, "Valor");
        assertTrue(VerificarCampos.verificarTemCamposNulos(obj), "Deve retornar true para objeto com campo nulo");
    }

    @Test
	@DisplayName("Verificar Campos Não Nulos")
    void verificarCamposNaoNulos() {
        ObjetoDeTeste obj = new ObjetoDeTeste("Valor", "OutroValor");
        assertFalse(VerificarCampos.verificarTemCamposNulos(obj), "Deve retornar false para objeto com todos os campos preenchidos");
    }

    // Teste para validarEmail
    
    @Test
	@DisplayName("Verificar Email Valido")
    void VerificarEmailValido() {
        assertTrue(VerificarCampos.validarEmail("test@example.com"), "Deve retornar true para email válido");
    }

    @Test
	@DisplayName("Verificar Emaiil Invalido")
    void VerificarEmailInvalido() {
        assertFalse(VerificarCampos.validarEmail("test@com"), "Deve retornar false para email inválido");
    }

    @Test
	@DisplayName("Verificar Email Nulos")
    void VerificarEmailNull() {
        assertFalse(VerificarCampos.validarEmail(null), "Deve retornar false para email null");
    }

    // Teste para validarSenha
    
    @Test
	@DisplayName("Verificar Senha Valida ")
    void VerificarSenhaValida() {
        assertTrue(VerificarCampos.validarSenha("Senha123!"), "Deve retornar true para senha válida");
    }

    @Test
	@DisplayName("Verificar Senha sem Numero ")
    void VerificarSenhaSemNumero() {
        assertFalse(VerificarCampos.validarSenha("Senha!"), "Deve retornar false para senha sem número");
    }

    @Test
	@DisplayName("Verificar Senha Sem Caracter Especial ")
    void VerificarSenhaSemCaractereEspecial() {
        assertFalse(VerificarCampos.validarSenha("Senha123"), "Deve retornar false para senha sem caractere especial");
    }

    @Test
	@DisplayName("Verificar Senha Muito Curta ")
    void VerificarSenhaMuitoCurta() {
        assertFalse(VerificarCampos.validarSenha("Ab1!"), "Deve retornar false para senha muito curta");
    }

    static class ObjetoDeTeste {
        private String campo1;
        private String campo2;

        public ObjetoDeTeste(String campo1, String campo2) {
            this.campo1 = campo1;
            this.campo2 = campo2;
        }
    }
}
