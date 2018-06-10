package com.wtx.fifachamp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by willi on 25/03/2018.
 */

public class Team implements Serializable {


    private int id;
    private String nome;
    private ArrayList<Jogador> jogadores;

    public Team() {
        this.jogadores = new ArrayList<>();
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

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
