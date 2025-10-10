package org.example.application.service;

import org.example.domain.model.PecasReposicao;
import org.example.domain.repository.PecaRepository;

public class PecaService {
    private final PecaRepository pecaRepository;

    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public String cadastrarPeca(PecasReposicao pecasReposicao) {
        if (pecasReposicao == null || pecasReposicao.getNome().isEmpty()) {
            throw new IllegalArgumentException("A Peça não Pode ser Nula.");
        }

        boolean pecaExiste = pecaRepository.buscarPecaExistente(pecasReposicao);

        if(pecaExiste) {
            return pecaRepository.cadastrarPeca(pecasReposicao);
        }else {
            return "A Peça "+pecasReposicao.getNome()+" Já Existe no Sistema";
        }
    }
}
