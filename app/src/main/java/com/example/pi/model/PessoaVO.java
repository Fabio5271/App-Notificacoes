package com.example.pi.model;

import java.util.List;

public class PessoaVO {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String matricula;
    private List<String> disciplinas;

    public PessoaVO() {
        // Construtor padrão
    }

    public PessoaVO(int id, String nome, String email, String senha, String matricula, List<String> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.matricula = matricula;
        this.disciplinas = disciplinas;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<String> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }

}
