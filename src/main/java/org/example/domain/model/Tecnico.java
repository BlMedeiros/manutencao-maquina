package org.example.domain.model;

public class Tecnico {
    private int idTecnico;
    private String nome;
    private String especialidade;

    public Tecnico(int idTecnico, String nome, String especialidade) {
        this.idTecnico = idTecnico;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Tecnico(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
