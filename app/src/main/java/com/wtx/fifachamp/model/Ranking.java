package com.wtx.fifachamp.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Ranking implements Serializable{
    private int numRodadas;
    private int numPartidas;
    private List<PlayerScore> playerScores;
    private Player playerArtilheiro;
    private Jogador artilheiro;
    private int golsArtilheiro;

    public Ranking() {
    }

    public Ranking(List<PlayerScore> playerScores) {
        this.playerScores = playerScores;
        apuraArtilheiro();
        ordenaRanking();
    }

    public int getNumRodadas() {
        return numRodadas;
    }

    public void setNumRodadas(int numRodadas) {
        this.numRodadas = numRodadas;
    }

    public int getNumPartidas() {
        return numPartidas;
    }

    public void setNumPartidas(int numPartidas) {
        this.numPartidas = numPartidas;
    }

    public List<PlayerScore> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(List<PlayerScore> playerScores) {
        this.playerScores = playerScores;
    }

    public Jogador getArtilheiro() {
        return artilheiro;
    }

    public int getGolsArtilheiro() {
        return golsArtilheiro;
    }

    public Player getPlayerArtilheiro() {
        return playerArtilheiro;
    }

    private void apuraArtilheiro(){
        int aux = 0;
        if(playerScores.size() > 0) {
            for (int i = 0; i < playerScores.size(); i++) {
                if (playerScores.get(aux).getGolsArtilheiro() < playerScores.get(i).getGolsArtilheiro())
                    aux = i;
            }

            this.artilheiro = playerScores.get(aux).getArtilheiro();
            this.golsArtilheiro = playerScores.get(aux).getGolsArtilheiro();

            //Atualiza a pontuação do player com o acrecimo pelo artilheiro
            if(this.playerScores.get(aux).getPts() > 0)
                this.playerScores.get(aux).setPts(playerScores.get(aux).getPts() + 5.0);

            this.playerArtilheiro = this.playerScores.get(aux).getPlayer();
        }else {
            this.artilheiro = new Jogador();
            this.golsArtilheiro = 0;
        }
    }

    private void ordenaRanking(){
        Collections.sort(this.playerScores, new PlayerScoreCompare());
        Collections.reverse(this.playerScores);
    }
}
