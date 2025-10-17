package org.example.application.service;

import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.enums.StatusOrdem;
import org.example.domain.repository.OrdemManutencaoRepository;

import java.time.LocalDate;

public class OrdemManutencaoService {

    OrdemManutencaoRepository ordemManutencaoRepository;

    public OrdemManutencaoService(OrdemManutencaoRepository ordemManutencaoRepository) {
        this.ordemManutencaoRepository = ordemManutencaoRepository;
    }

    public String cadastrarOrdemManutencao(OrdemManutencao ordemManutencao) {
        if(ordemManutencao == null) {
            throw new RuntimeException("A Ordem de Manutenção Não Pode ser Nula.");
        }

        ordemManutencao.setDataSolicitacao(LocalDate.now());
        ordemManutencao.setStatusOrdem(StatusOrdem.PENDENTE);

        return ordemManutencaoRepository.cadastrarOrdemManutencao(ordemManutencao);
    }
}
