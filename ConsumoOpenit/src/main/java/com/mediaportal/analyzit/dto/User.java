package com.mediaportal.analyzit.dto;

/**
 *
 * @author Giovanny
 */
public class User {
    private String nome;
    private String date;
    private String sessao;
    
    public User(String nome, String date, String sessao) {
        this.nome = nome;
        this.date = date;
        this.sessao = sessao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDate() {
        return date;
    }

    public void setUserID(String date) {
        this.date = date;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }
}
