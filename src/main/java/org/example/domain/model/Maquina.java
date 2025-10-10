package org.example.domain.model;

import org.example.domain.model.enums.MaquinaStatus;

public class Maquina {
    private int idMaquina;
    private String nome;
    private String setor;
    private MaquinaStatus maquinaStatus;

    public Maquina(int idMaquina, String nome, String setor, MaquinaStatus maquinaStatus) {
        this.idMaquina = idMaquina;
        this.nome = nome;
        this.setor = setor;
        this.maquinaStatus = maquinaStatus;
    }

    public Maquina(String nome, String setor) {
        this.nome = nome;
        this.setor = setor;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public MaquinaStatus getMaquinaStatus() {
        return maquinaStatus;
    }

    public void setMaquinaStatus(MaquinaStatus maquinaStatus) {
        this.maquinaStatus = maquinaStatus;
    }
}
