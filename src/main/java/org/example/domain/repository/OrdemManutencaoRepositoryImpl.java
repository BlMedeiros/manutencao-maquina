package org.example.domain.repository;

import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.enums.MaquinaStatus;
import org.example.domain.repository.connection.ConnectionDatabase;

import java.sql.*;

public class OrdemManutencaoRepositoryImpl implements OrdemManutencaoRepository{


    @Override
    public String cadastrarOrdemManutencao(OrdemManutencao ordemManutencao) {
        String insertQuery = """
                INSERT INTO ordemmanutencao(idMaquina,idTecnico,dataSolicitacao,status)
                VALUES(?,?,?,?)
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(insertQuery)) {

            stmt.setInt(1,ordemManutencao.getIdMaquina());
            stmt.setInt(2,ordemManutencao.getIdTecnico());
            stmt.setDate(3,Date.valueOf(ordemManutencao.getDataSolicitacao()));
            stmt.setString(4, String.valueOf(ordemManutencao.getStatusOrdem()));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return "A Ordem de Manutenção Foi Cadastrada.";
            } else {
                return "Ocorreu um Erro ao Cadastrar a Ordem de Manutenção.";
            }
        }catch (SQLException e) {
            return "Ocorreu um Erro ao Cadastrar "+e;
        }
    }
}
