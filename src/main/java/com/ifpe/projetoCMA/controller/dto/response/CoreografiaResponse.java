package com.ifpe.projetoCMA.controller.dto.response;

import java.util.List;

public record CoreografiaResponse(Long coreoID, String nome, String aprendizado, String conhecimento, QuestionarioResponse questionario , List<AtividadeResponse> atividades ) {

}
