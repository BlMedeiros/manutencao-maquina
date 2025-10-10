package org.example.domain.repository;

import org.example.domain.model.Tecnico;

public interface TecnicoRepository {

    String cadastrarTecnico(Tecnico tecnico);

    boolean buscarTecnicoExistente(Tecnico tecnico);
}
