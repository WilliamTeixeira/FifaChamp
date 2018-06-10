package com.wtx.fifachamp.model;

import java.io.Serializable;

/**
 * Created by willi on 25/03/2018.
 */

public class Player implements Serializable {

    private int id;
    private String nome;
    private Team team;


    public Player() {
    }

    public Player(int id, String nome, Team team) {
        this.id = id;
        this.nome = nome;
        this.team = team;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
