package org.example.domain.repository;

import org.example.domain.model.OrdemManutencao;

import java.sql.SQLException;
import java.util.List;

public interface OrdemManutencaoRepository {

    String cadastrarOrdemManutencao(OrdemManutencao ordemManutencao);

    List<OrdemManutencao> listarOrdemManutencaoPendente() throws SQLException;
}
