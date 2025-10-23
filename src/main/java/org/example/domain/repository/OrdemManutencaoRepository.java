package org.example.domain.repository;

import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.PecasReposicao;
import org.example.domain.model.enums.StatusOrdem;

import java.sql.SQLException;
import java.util.List;

public interface OrdemManutencaoRepository {

    String cadastrarOrdemManutencao(OrdemManutencao ordemManutencao);

    List<OrdemManutencao> listarOrdemManutencaoPendente() throws SQLException;

    String cadastrarOrdemItem(Integer idOrdem, List<PecasReposicao> pecasReposicaoList) throws SQLException;

    OrdemManutencao buscarOrdemComItens(int idOrdem) throws SQLException;

    void atualizarStatus(int idOrdem, StatusOrdem status);
}
