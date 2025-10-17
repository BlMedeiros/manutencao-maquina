package org.example.domain.model;

import org.example.domain.model.enums.StatusOrdem;

import java.time.LocalDate;

public class OrdemManutencao {
    private int idOrdem;
    private int idMaquina;
    private int idTecnico;
    private LocalDate dataSolicitacao;
    private StatusOrdem statusOrdem;

    public OrdemManutencao(int idOrdem, int idMaquina, int idTecnico, LocalDate dataSolicitacao, StatusOrdem statusOrdem) {
        this.idOrdem = idOrdem;
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.statusOrdem = statusOrdem;
    }

    public OrdemManutencao(int idMaquina, int idTecnico) {
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public StatusOrdem getStatusOrdem() {
        return statusOrdem;
    }

    public void setStatusOrdem(StatusOrdem statusOrdem) {
        this.statusOrdem = statusOrdem;
    }
}
