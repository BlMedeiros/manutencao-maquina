package org.example.domain.repository;

import org.example.domain.model.Maquina;
import org.example.domain.model.enums.MaquinaStatus;
import org.example.domain.repository.connection.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaquinaRepositoryImpl implements MaquinaRepository {

    @Override
    public String cadastrarMaquina(Maquina maquina) {
        String insertQuery = """
                INSERT INTO Maquina(nome,setor,status)
                VALUES ?,?,?
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

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
