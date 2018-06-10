package com.wtx.fifachamp.model;

import java.io.Serializable;

public class PlayerScore extends PlayerScoreCompare implements Serializable {
    private int id;
    private Player player;
    private int qtdJogos;
    private int qtdGols;
    private Jogador artilheiro;
    private int golsArtilheiro;
    private double pts;

    public PlayerScore(Player player) {
        this.player = player;
        this.id = player.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getQtdJogos() {
        return qtdJogos;
    }

    public void setQtdJogos(int qtdJogos) {
        this.qtdJogos = qtdJogos;
    }

    public int getQtdGols() {
        return qtdGols;
    }

    public void setQtdGols(int qtdGols) {
        this.qtdGols = qtdGols;
    }

    public Jogador getArtilheiro() {
        return artilheiro;
    }

    public void setArtilheiro(Jogador artilheiro) {
        this.artilheiro = artilheiro;
    }

    public int getGolsArtilheiro() {
        return golsArtilheiro;
    }

    public void setGolsArtilheiro(int golsArtilheiro) {
        this.golsArtilheiro = golsArtilheiro;
    }

    public double getPts() {
        return pts;
    }

    public void setPts(double pts) {
        this.pts = pts;
    }
}
