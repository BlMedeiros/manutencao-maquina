package org.example.persistence.dao;

import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.enums.MaquinaStatus;
import org.example.domain.repository.MaquinaRepository;
import org.example.persistence.connection.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaRepositoryImpl implements MaquinaRepository {

    @Override
    public String cadastrarMaquina(Maquina maquina) {
        String insertQuery = """
                INSERT INTO Maquina(nome,setor,status)
                VALUES (?,?,?)
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(insertQuery)) {

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, MaquinaStatus.OPERACIONAL.toString());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return "A Maquina " + maquina.getNome() + " Foi Cadastrada ao Sistema";
            } else {
                return "Ocorreu um Erro ao Cadastrar a Maquina " + maquina.getNome();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean buscarMaquinaExistente(Maquina maquina) {
        String selectQuery = """
                SELECT nome,setor
                FROM maquina
                WHERE nome = ? AND setor = ?
                """;
        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(selectQuery)) {

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());

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
    public List<Maquina> listarMaquinaOperacional() throws SQLException {
        String selectQuery = """
                SELECT id,nome,setor,status
                FROM maquina
                WHERE status = 'OPERACIONAL';
                """;

        List<Maquina> maquinas = new ArrayList<>();
        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(selectQuery)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String setor = rs.getString("setor");
                String status = rs.getString("status");

                MaquinaStatus maquinaStatus = MaquinaStatus.valueOf(status);

                Maquina maquina = new Maquina(id, nome, setor, maquinaStatus);

                maquinas.add(maquina);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return maquinas;
    }

    @Override
    public void atualizarStatusMaquina(OrdemManutencao ordemManutencao) {
        String updateQuery = """
                UPDATE maquina
                SET status = 'EM_MANUTENCAO'
                WHERE id = ?
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(updateQuery)) {

            stmt.setInt(1, ordemManutencao.getIdMaquina());

            stmt.executeUpdate();

            // implementar algum retorno ao usuario

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
