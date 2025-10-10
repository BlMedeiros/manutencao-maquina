package org.example.domain.repository;

import org.example.domain.model.Tecnico;
import org.example.domain.repository.connection.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TecnicoRepositoryImpl implements TecnicoRepository {
    @Override
    public String cadastrarTecnico(Tecnico tecnico) {
        String insertQuery = """
                INSERT INTO tecnico(nome,especialidade)
                VALUES ?,?
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(insertQuery)) {

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return "O Tecnico(a) " + tecnico.getNome() + " foi Cadastrado(a) ao Sistema.";
            } else {
                throw new RuntimeException("O Tecnico(a) não foi Cadastrado(a) ao Sistema");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean buscarTecnicoExistente(Tecnico tecnico) {
        String selectQuery = """
                SELECT nome,especialidade
                FROM tecnico
                WHERE nome = ? AND especialidade = ?
                """;

        try (Connection con = ConnectionDatabase.conectar();
             PreparedStatement stmt = con.prepareStatement(selectQuery)) {

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());

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
