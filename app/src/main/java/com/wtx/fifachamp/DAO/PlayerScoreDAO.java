package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wtx.fifachamp.model.Jogador;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.PlayerScore;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerScoreDAO  {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public PlayerScoreDAO (Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public List<PlayerScore> findAll(){
        String[] campos = {"idplayer", "nome", "idteam"};

        ArrayList<PlayerScore> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.query("player",campos,null,null,null,null,null);
            //Instancia a lista
            objs = new ArrayList<>();
            //preenche a lista
            while (cursor.moveToNext()){
                int idPlayer = cursor.getInt(cursor.getColumnIndex("idplayer"));
                PlayerScore obj = find(idPlayer);
                if(obj != null)
                    objs.add(obj);
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método findAll: "+ e.getMessage());
        }

        return objs;
    }

    public PlayerScore find(int idPlayer){
        String sqlQtdJogos =   "SELECT count(*) qtdjogos FROM partida WHERE idplayerone = '" + idPlayer +
                               "' OR idplayertwo = '" + idPlayer + "';";
        String sqlQtdGols01  = "SELECT sum(golsone) as gols1 FROM partida WHERE idplayerone = '" + idPlayer + "';";
        String sqlQtdGols02  = "SELECT sum(golstwo) as gols2 FROM partida WHERE idplayertwo = '" + idPlayer + "';";
        String sqlArtilheiro = "SELECT idjogador, sum(gols) as qtdgolartilheiro FROM golpartida WHERE idplayer = '" +
                                idPlayer + "' GROUP BY idjogador ORDER BY qtdgolartilheiro desc limit 1";

        PlayerScore obj = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            //Cursor cPlayer = conexao.rawQuery(sqlQtdJogos, null);
            Cursor cJogos = conexao.rawQuery(sqlQtdJogos, null);
            Cursor cGols1 = conexao.rawQuery(sqlQtdGols01, null);
            Cursor cGols2 = conexao.rawQuery(sqlQtdGols02, null);
            Cursor cArtilheiro= conexao.rawQuery(sqlArtilheiro, null);

            //preenche a lista
            if (cJogos.moveToNext() && cGols1.moveToNext() && cGols2.moveToNext() && cArtilheiro.moveToNext()){
                obj = preencheObj(idPlayer,cJogos,cGols1,cGols2,cArtilheiro);
            }else {
                obj = preencheObj(idPlayer,null,null,null,null);
            }

            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método find: "+ e.getMessage());
        }

        return obj;
    }



    private PlayerScore preencheObj(int idPlayer, Cursor cJogos, Cursor cGols1, Cursor cGols2, Cursor cArtilheiro){

        Player player = new PlayerDAO(this.context).find(idPlayer);

        PlayerScore obj = new PlayerScore(player);
        if (cJogos != null && (cGols1 != null || cGols2 != null) && cArtilheiro != null) {

            obj.setQtdJogos(cJogos.getInt(cJogos.getColumnIndex("qtdjogos")));

            int gol1 = cGols1.getInt(cGols1.getColumnIndex("gols1"));
            int gol2 = cGols2.getInt(cGols2.getColumnIndex("gols2"));
            obj.setQtdGols(gol1 + gol2);

            Jogador artilheiro = new JogadorDAO(this.context).find(cArtilheiro.getInt(cArtilheiro.getColumnIndex("idjogador")));
            obj.setArtilheiro(artilheiro);

            obj.setGolsArtilheiro(cArtilheiro.getInt(cArtilheiro.getColumnIndex("qtdgolartilheiro")));

/*            List<Partida> partidas = new PartidaDAO(this.context).findAllByPlayer(idPlayer);

            double pts = apuraPontuacao(idPlayer, partidas);
            obj.setPts(pts);*/
        }else{
            obj.setQtdJogos(0);
            obj.setQtdGols(0);
            obj.setArtilheiro(new JogadorDAO(this.context).findAllByTeam(player.getTeam().getId()).get(0));
            obj.setGolsArtilheiro(0);
            //obj.setPts(0);
        }

        List<Partida> partidas = new PartidaDAO(this.context).findAllByPlayer(idPlayer);

        double pts = apuraPontuacao(idPlayer, partidas);
        obj.setPts(pts);

        return obj;
    }

    private double apuraPontuacao(int idPlayer, List<Partida> partidas) {

        double pts = 0;

        for(Partida p : partidas){
            if (idPlayer == p.getPlayerOne().getId()) {
                pts += p.getPtsOne();
            }
            else {
                pts += p.getPtsTwo();
            }
        }

        return pts;
    }

}
