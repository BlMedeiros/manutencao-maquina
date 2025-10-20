package org.example.domain.repository;

import org.example.domain.model.PecasReposicao;

import java.util.List;

public interface PecaRepository {

    String cadastrarPeca(PecasReposicao pecasReposicao);

    boolean buscarPecaExistente(PecasReposicao pecasReposicao);

    List<PecasReposicao> listarPecas();
}
