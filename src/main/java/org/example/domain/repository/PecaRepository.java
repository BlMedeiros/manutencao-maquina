package org.example.domain.repository;

import org.example.domain.model.PecasReposicao;

public interface PecaRepository {

    String cadastrarPeca(PecasReposicao pecasReposicao);

    boolean buscarPecaExistente(PecasReposicao pecasReposicao);
}
