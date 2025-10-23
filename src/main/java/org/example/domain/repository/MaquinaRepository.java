package org.example.domain.repository;

import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;

import java.sql.SQLException;
import java.util.List;

public interface MaquinaRepository {

    String cadastrarMaquina(Maquina maquina);

    boolean buscarMaquinaExistente(Maquina maquina);

    List<Maquina> listarMaquinaOperacional() throws SQLException;

    void atualizarStatusMaquina(OrdemManutencao ordemManutencao);

    void atualizarStatusMaquinaOperacional(int idMaquina);
}
