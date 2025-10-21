package org.example.domain.model;

public class PecasReposicao {
    private int idPeca;
    private String nome;
    private Double estoque;

    public PecasReposicao(int idPeca, String nome, Double estoque) {
        this.idPeca = idPeca;
        this.nome = nome;
        this.estoque = estoque;
    }

    public PecasReposicao(String nome, Double estoque) {
        this.nome = nome;
        this.estoque = estoque;
    }

    public PecasReposicao(int idPeca, Double estoque) {
        this.idPeca = idPeca;
        this.estoque = estoque;
    }

    public int getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }
}
