package com.wtx.fifachamp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by willi on 30/03/2018.
 */

public class Partida implements Serializable{

    private int id;
    private int idRodada;

    private Player playerOne;
    private int golsOne;
    private double ptsOne;

    private Player playerTwo;
    private int golsTwo;
    private double ptsTwo;

    private ArrayList<GolPartida> golPartidas;

    public Partida() {
    }

    public Partida(int idRodada, Player playerOne, Player playerTwo ) {
        this.idRodada = idRodada;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRodada() {
        return idRodada;
    }

    public void setIdRodada(int idRodada) {
        this.idRodada = idRodada;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public int getGolsOne() {
        return golsOne;
    }

    public void setGolsOne(int golsOne) {
        this.golsOne = golsOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getGolsTwo() {
        return golsTwo;
    }

    public void setGolsTwo(int golsTwo) {
        this.golsTwo = golsTwo;
    }

    public ArrayList<GolPartida> getGolPartidas() {
        return golPartidas;
    }

    public void setGolPartidas(ArrayList<GolPartida> golPartidas) {
        this.golPartidas = golPartidas;
    }

    public double getPtsOne() {
        return ptsOne;
    }

    public double getPtsTwo() {
        return ptsTwo;
    }


    public void atualizaPts(){
        this.atualizaPtsPlayerOne();
        this.atualizaPtsPlayerTwo();
    }

    //region Pontuação da Partida

    private void atualizaPtsPlayerOne(){
        //Cada gol 1 pts
        double pts = this.golsOne;

        //Cada gol tomado - 0,5 pts
        if(this.golsTwo > 0)
            pts -= (this.golsTwo * 0.5);

        //Vitoria 3 pts
        if (this.golsOne > this.golsTwo)
            pts += 3.0;

        this.ptsOne = pts;
    }
    private void  atualizaPtsPlayerTwo(){
        //Cada gol 1 pts
        double pts = this.golsTwo;

        //Cada gol tomado - 0,5 pts
        if(this.golsOne > 0)
            pts -= (this.golsOne * 0.5);

        //Vitoria 3 pts
        if (this.golsTwo > this.golsOne)
            pts += 3.0;

        this.ptsTwo = pts;
    }

    //endregion
}
