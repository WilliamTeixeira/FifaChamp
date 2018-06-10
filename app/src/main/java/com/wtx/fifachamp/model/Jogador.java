package com.wtx.fifachamp.model;

import java.io.Serializable;

/**
 * Created by willi on 25/03/2018.
 */

public class Jogador implements Serializable{

    private int id;
    private String nome;
    private int idTeam;

    public Jogador() {
    }

    public Jogador(int id, String nome, int idTeam) {
        this.id = id;
        this.nome = nome;
        this.idTeam = idTeam;
    }

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

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }
}
