package com.wtx.fifachamp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Rodada implements Serializable {
    private int id;
    private int totalPartidas;
    private int totalGols;
    private ArrayList<Player> players;
    private ArrayList<Partida> partidas;

    public Rodada() {

    }

    public Rodada(int id, ArrayList<Player> players) {
        this.players = players;
        this.partidas = new ArrayList<>();
        this.id = id;
        geraRodada();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public int getTotalPartidas() {
        return totalPartidas;
    }

    public void setTotalPartidas(int totalPartidas) {
        this.totalPartidas = totalPartidas;
    }

    public int getTotalGols() {
        return totalGols;
    }

    public void setTotalGols(int totalGols) {
        this.totalGols = totalGols;
    }

    private void geraRodada(){

        //pega um player aleatoriamente, adiciona à partida, e remove da lista de players até que a mesma esteja vazia
        Random rand = new Random();
        while (players.size() > 0) {
            Player p1 = players.get(rand.nextInt(players.size()));
            players.remove(p1);

            Player p2 =players.get(rand.nextInt(players.size()));
            players.remove(p2);

            Partida partida = new Partida(this.id, p1, p2);
            partidas.add(partida);
        }

        //atualiza o total de partidas da rodada
        this.totalPartidas = partidas.size();
    }
}