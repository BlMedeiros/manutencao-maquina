package org.example.domain.repository;

import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.enums.MaquinaStatus;
import org.example.domain.model.enums.StatusOrdem;
import org.example.domain.repository.connection.ConnectionDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<OrdemManutencao> listarOrdemManutencaoPendente() throws SQLException {
        List<OrdemManutencao> ordemManutencaoList = new ArrayList<>();

        String selectQuery = """
                SELECT id,idMaquina,idTecnico,dataSolicitacao,status
                FROM ordemmanutencao
                WHERE status = 'PENDENTE'
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(selectQuery)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int idOrdem = rs.getInt("id");
                int idMaquina = rs.getInt("idMaquina");
                int idTecnico = rs.getInt("idTecnico");
                LocalDate dataSolicitacao = rs.getDate("dataSolicitacao").toLocalDate();

                String statusString = rs.getString("status");

                StatusOrdem statusOrdem = StatusOrdem.valueOf(statusString.toUpperCase());

                ordemManutencaoList.add(new OrdemManutencao(idOrdem,idMaquina,idTecnico,dataSolicitacao,statusOrdem));
            }
        }
        return ordemManutencaoList;
    }
}
