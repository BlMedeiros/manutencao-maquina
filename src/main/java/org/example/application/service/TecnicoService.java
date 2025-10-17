package org.example.application.service;

import org.example.domain.model.Tecnico;
import org.example.domain.repository.TecnicoRepository;

import java.util.List;

public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public String cadastrarTecnico(Tecnico tecnico) {
        if(tecnico == null) {
            throw new IllegalArgumentException("O Técnico(a) não pode ser Nulo.");
        }
        boolean tecnicoExiste = tecnicoRepository.buscarTecnicoExistente(tecnico);

        if(!tecnicoExiste) {
            return tecnicoRepository.cadastrarTecnico(tecnico);
        } else {
            return "O Técnico(a)"+tecnico.getNome()+" já Existe no Sistema";
        }
    }

    public List<Tecnico> listarTodosTecnicos() {
        return tecnicoRepository.listarTodosTecnicos();
    }
}
