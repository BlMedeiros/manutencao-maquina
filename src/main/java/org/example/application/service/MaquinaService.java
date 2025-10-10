package org.example.application.service;

import org.example.domain.model.Maquina;
import org.example.domain.repository.MaquinaRepository;

public class MaquinaService {

    private final MaquinaRepository maquinaRepository;

    public MaquinaService(MaquinaRepository maquinaRepository) {
        this.maquinaRepository = maquinaRepository;
    }

    public String cadastrarMaquina(Maquina maquina) {
        if (maquina == null) {
            throw new IllegalArgumentException("A Maquina não Pode ser Nula.");
        }

        boolean maquinaExiste = maquinaRepository.buscarMaquinaExistente(maquina);

        if (!maquinaExiste) {
            return maquinaRepository.cadastrarMaquina(maquina);
        }else {
            return "A Máquina ja foi Cadastrada no Sistema";
        }
    }
}
