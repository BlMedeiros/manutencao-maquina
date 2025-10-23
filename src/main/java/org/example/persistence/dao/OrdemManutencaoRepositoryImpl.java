package org.example.persistence.dao;

import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.PecasReposicao;
import org.example.domain.model.enums.StatusOrdem;
import org.example.domain.repository.OrdemManutencaoRepository;
import org.example.persistence.connection.ConnectionDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemManutencaoRepositoryImpl implements OrdemManutencaoRepository {


    @Override
    public String cadastrarOrdemManutencao(OrdemManutencao ordemManutencao) {
        String insertQuery = """
                INSERT INTO ordemmanutencao(idMaquina,idTecnico,dataSolicitacao,status)
                VALUES(?,?,?,?)
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(insertQuery)) {

            stmt.setInt(1, ordemManutencao.getIdMaquina());
            stmt.setInt(2, ordemManutencao.getIdTecnico());
            stmt.setDate(3, Date.valueOf(ordemManutencao.getDataSolicitacao()));
            stmt.setString(4, String.valueOf(ordemManutencao.getStatusOrdem()));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return "A Ordem de Manutenção Foi Cadastrada.";
            } else {
                return "Ocorreu um Erro ao Cadastrar a Ordem de Manutenção.";
            }
        } catch (SQLException e) {
            return "Ocorreu um Erro ao Cadastrar " + e;
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

                ordemManutencaoList.add(new OrdemManutencao(idOrdem, idMaquina, idTecnico, dataSolicitacao, statusOrdem));
            }
        }
        return ordemManutencaoList;
    }

    @Override
    public String cadastrarOrdemItem(Integer idOrdem, List<PecasReposicao> pecasReposicaoList) throws SQLException {
        String insertQuery = """
                INSERT INTO ordempeca(idOrdem,idPeca,quantidade)
                VALUES(?,?,?)
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(insertQuery)) {

            stmt.setInt(1, idOrdem);

            pecasReposicaoList.forEach(pecasReposicao -> {
                try {
                    stmt.setInt(2, pecasReposicao.getIdPeca());
                    stmt.setDouble(3, pecasReposicao.getEstoque());

                    int linhasAfetadas = stmt.executeUpdate();

                    if(linhasAfetadas > 0) {
                        System.out.println("Peça de Id "+pecasReposicao.getIdPeca()+" Cadastrada a Ordem!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return "Cadastro Bem-Sucedido";
    }

        @Override
        public OrdemManutencao buscarOrdemComItens(int idOrdem) throws SQLException {
            OrdemManutencao ordem = null;
            List<PecasReposicao> pecasUtilizadas = new ArrayList<>();

            String sqlOrdem = """
                    SELECT id, idMaquina, idTecnico, dataSolicitacao, status
                    FROM ordemmanutencao WHERE id = ?
                    """;

            try (Connection con = ConnectionDatabase.conectar();
                 PreparedStatement stmt = con.prepareStatement(sqlOrdem)) {

                stmt.setInt(1, idOrdem);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        ordem = new OrdemManutencao(
                                rs.getInt("id"),
                                rs.getInt("idMaquina"),
                                rs.getInt("idTecnico"),
                                rs.getDate("dataSolicitacao").toLocalDate(),
                                StatusOrdem.valueOf(rs.getString("status").toUpperCase())
                        );
                    } else {
                        return null;
                    }
                }

                if (ordem != null) {
                    String sqlItens = """
                            SELECT op.idPeca, op.quantidade, p.nome FROM ordempeca op
                            JOIN peca p ON op.idPeca = p.id
                            WHERE op.idOrdem = ?
                            """;

                    try (PreparedStatement psItens = con.prepareStatement(sqlItens)) {
                        psItens.setInt(1, idOrdem);
                        try (ResultSet rsItens = psItens.executeQuery()) {
                            while (rsItens.next()) {
                                int idPeca = rsItens.getInt("idPeca");
                                double quantidade = rsItens.getDouble("quantidade");
                                String nomePeca = rsItens.getString("nome");

                                PecasReposicao peca = new PecasReposicao(idPeca, nomePeca, 0.0);
                                peca.setEstoque(quantidade);
                                pecasUtilizadas.add(peca);
                            }
                        }
                    }

                    ordem.setPecasUtilizadas(pecasUtilizadas);
                }
            }


            return ordem;
        }

    public void atualizarStatus(int idOrdem, StatusOrdem status) {
        String sql = "UPDATE ordemmanutencao SET status = ? WHERE id = ?";

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, idOrdem);

            stmt.executeUpdate();

        }catch(SQLException e) {
            System.err.println("Ocorreu um Erro no Banco de Dados: "+e);
        }
    }
}
