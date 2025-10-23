package org.example.domain.repository;

import org.example.domain.model.PecasReposicao;

import java.sql.SQLException;
import java.util.List;

public interface PecaRepository {

    String cadastrarPeca(PecasReposicao pecasReposicao);

    boolean buscarPecaExistente(PecasReposicao pecasReposicao);

    List<PecasReposicao> listarPecas();

    void baixarEstoque(List<PecasReposicao> pecas) throws SQLException;
}
