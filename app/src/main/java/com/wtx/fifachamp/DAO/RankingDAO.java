package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.PlayerScore;
import com.wtx.fifachamp.model.Ranking;
import com.wtx.fifachamp.model.Rodada;

import java.util.List;

public class RankingDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public RankingDAO (Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    public Ranking find(){
        List<PlayerScore> playerScores = new PlayerScoreDAO(this.context).findAll();
        List<Rodada> rodadas = new RodadaDAO(this.context).findAll();
        Ranking obj;

        if(rodadas.size() > 0) {
            obj = new Ranking(playerScores);

            obj.setNumRodadas(rodadas.size());

            int partidas = 0;
            for (Rodada r : rodadas)
                partidas += r.getTotalPartidas();

            obj.setNumPartidas(partidas);
        }else {
            obj = null;
        }
        return obj;
    }
}
