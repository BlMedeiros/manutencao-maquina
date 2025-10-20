package org.example.application.service;

import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.enums.StatusOrdem;
import org.example.domain.repository.MaquinaRepository;
import org.example.domain.repository.OrdemManutencaoRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdemManutencaoService {

    OrdemManutencaoRepository ordemManutencaoRepository;
    MaquinaRepository maquinaRepository;

    public OrdemManutencaoService(OrdemManutencaoRepository ordemManutencaoRepository, MaquinaRepository maquinaRepository) {
        this.ordemManutencaoRepository = ordemManutencaoRepository;
        this.maquinaRepository = maquinaRepository;
    }

    public String cadastrarOrdemManutencao(OrdemManutencao ordemManutencao) {
        if(ordemManutencao == null) {
            throw new RuntimeException("A Ordem de Manutenção Não Pode ser Nula.");
        }

        ordemManutencao.setDataSolicitacao(LocalDate.now());
        ordemManutencao.setStatusOrdem(StatusOrdem.PENDENTE);

        maquinaRepository.atualizarStatusMaquina(ordemManutencao);

        return ordemManutencaoRepository.cadastrarOrdemManutencao(ordemManutencao);
    }

    public List<OrdemManutencao> listarOrdemManutencaoPendente() throws SQLException {
        return ordemManutencaoRepository.listarOrdemManutencaoPendente();
    }
}
