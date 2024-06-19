package com.example.pi.model;

import java.util.Date;

public class NotasVO {
  private int id;
    private String titulo;
    private String descricao;
    private int idCriador;
    private Date dataCriacao;
    private String disciplina;
    private double valorNota;

    public NotasVO() {
        // Construtor padrão
    }

    public NotasVO(int id, String titulo, String descricao, int idCriador, Date dataCriacao, String disciplina, double valorNota) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.idCriador = idCriador;
        this.dataCriacao = dataCriacao;
        this.disciplina = disciplina;
        this.valorNota = valorNota;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public double getValorNota() {
        return valorNota;
    }

    public void setValorNota(double valorNota) {
        this.valorNota = valorNota;
    }
}