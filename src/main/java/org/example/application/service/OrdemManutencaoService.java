package org.example.application.service;

import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.PecasReposicao;
import org.example.domain.model.enums.MaquinaStatus;
import org.example.domain.model.enums.StatusOrdem;
import org.example.domain.repository.MaquinaRepository;
import org.example.domain.repository.OrdemManutencaoRepository;
import org.example.domain.repository.PecaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdemManutencaoService {

    PecaRepository pecaRepository;
    OrdemManutencaoRepository ordemManutencaoRepository;
    MaquinaRepository maquinaRepository;

    public OrdemManutencaoService(OrdemManutencaoRepository ordemManutencaoRepository, MaquinaRepository maquinaRepository,PecaRepository pecaRepository) {
        this.ordemManutencaoRepository = ordemManutencaoRepository;
        this.maquinaRepository = maquinaRepository;
        this.pecaRepository = pecaRepository;
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

    public String cadastrarOrdemItem(Integer idOrdem, List<PecasReposicao> pecasReposicaoList) throws SQLException {
        return ordemManutencaoRepository.cadastrarOrdemItem(idOrdem,pecasReposicaoList);
    }

    public OrdemManutencao buscarOrdemComItens(int idOrdem) throws SQLException {
        return ordemManutencaoRepository.buscarOrdemComItens(idOrdem);
    }

    public void executarManutencao(int idOrdem, int idMaquina, List<PecasReposicao> pecasReposicaoList) throws SQLException {
        try {
                pecaRepository.baixarEstoque(pecasReposicaoList);

                ordemManutencaoRepository.atualizarStatus(idOrdem, StatusOrdem.EXECUTADA);

            maquinaRepository.atualizarStatusMaquinaOperacional(idMaquina);



        } catch (SQLException e) {

            throw new SQLException("Erro ao executar manutenção transacionalmente.", e);
        }
    }


}
