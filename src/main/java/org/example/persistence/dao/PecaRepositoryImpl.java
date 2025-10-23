package org.example.persistence.dao;

import org.example.domain.model.PecasReposicao;
import org.example.domain.repository.PecaRepository;
import org.example.persistence.connection.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaRepositoryImpl implements PecaRepository {

    @Override
    public String cadastrarPeca(PecasReposicao pecasReposicao) {
        String insertQuery = """
                INSERT INTO peca(nome,estoque)
                VALUES (?,?)
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(insertQuery)) {

            stmt.setString(1, pecasReposicao.getNome());
            stmt.setDouble(2, pecasReposicao.getEstoque());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return "A Peça " + pecasReposicao.getNome() + " Foi Cadastrada no Sistema";
            } else {
                return "A Peça" + pecasReposicao.getNome() + " Não Foi Cadastrada no Sistema";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean buscarPecaExistente(PecasReposicao pecasReposicao) {
        String selectQuery = """
                SELECT nome
                FROM peca
                WHERE nome = ?
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(selectQuery)) {

            stmt.setString(1, pecasReposicao.getNome());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PecasReposicao> listarPecas() {
        String selectQuery = """
                SELECT id,nome,estoque
                FROM peca
                """;

        List<PecasReposicao> reposicaoList = new ArrayList<>();

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(selectQuery)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idPeca = rs.getInt("id");
                String nomePeca = rs.getString("nome");
                Double quantidadePeca = rs.getDouble("estoque");

                reposicaoList.add(new PecasReposicao(idPeca,nomePeca,quantidadePeca));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reposicaoList;
    }

    public void baixarEstoque(List<PecasReposicao> pecas) throws SQLException {
        String sql = "UPDATE peca SET estoque = estoque - ? WHERE id = ?";

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (PecasReposicao peca : pecas) {
                stmt.setDouble(1, peca.getEstoque());
                stmt.setInt(2, peca.getIdPeca());
                stmt.addBatch();
            }

            int[] linhasAfetadas = stmt.executeBatch();

            for (int count : linhasAfetadas) {
                if (count == 0) {
                    System.err.println("Aviso: Baixa de estoque falhou para uma das peças (ID ou quantidade inválida).");
                }
            }
        }
    }
}
