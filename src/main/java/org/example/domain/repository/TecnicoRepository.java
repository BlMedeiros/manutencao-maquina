package org.example.domain.repository;

import org.example.domain.model.Tecnico;

import java.util.List;

public interface TecnicoRepository {

    String cadastrarTecnico(Tecnico tecnico);

    boolean buscarTecnicoExistente(Tecnico tecnico);

    List<Tecnico> listarTodosTecnicos();
}
