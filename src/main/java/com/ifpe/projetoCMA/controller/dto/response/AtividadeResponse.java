package com.ifpe.projetoCMA.controller.dto.response;

import java.util.List;

public record AtividadeResponse(Long atividadeID, String nome, List<String> conteudo) {

}
