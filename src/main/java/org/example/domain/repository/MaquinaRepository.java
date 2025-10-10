package org.example.domain.repository;

import org.example.domain.model.Maquina;

public interface MaquinaRepository {

    String cadastrarMaquina(Maquina maquina);

    boolean buscarMaquinaExistente(Maquina maquina);
}
