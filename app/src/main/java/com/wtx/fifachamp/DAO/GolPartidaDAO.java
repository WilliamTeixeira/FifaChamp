package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wtx.fifachamp.model.GolPartida;
import com.wtx.fifachamp.model.Jogador;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Rodada;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class GolPartidaDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public GolPartidaDAO(Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public void insert(GolPartida obj){

        String sql = "INSERT INTO golpartida ( idpartida, idplayer, idjogador, gols ) " +
                     "VALUES ( '" + obj.getIdPartida() + "', '" +
                                    obj.getPlayer().getId() + "', '" +
                                    obj.getJogador().getId() + "', '" +
                                    obj.getGols() + "' );";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método insert: " + e.getMessage());
        }

    }

    public void update(GolPartida obj){
        String sql = "UPDATE golpartida SET " +
                " idpartida = '"    + obj.getIdPartida() +
                "', idplayer = '"   + obj.getPlayer().getId() +
                "', idjogador = '"  + obj.getJogador().getId() +
                "', gols = '"       + obj.getGols() +
                "' WHERE idgolpartida = '" + obj.getId() + "';";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método update: " + e.getMessage());
        }
    }

    public void delete(GolPartida obj){
        String sql = "DELETE FROM golpartida WHERE idgolpartida = '" + obj.getId() + "';";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método delete: " + e.getMessage());
        }
    }

    public void deleteAll(ArrayList<Partida> objs) {
        int i;

        try {
            //Abre a conexao
            open();

            //Percorre todas as partidas, deletando os gols
            for(i = 0; i < objs.size(); i++) {
                if(objs.get(i).getGolsOne() > 0 || objs.get(i).getGolsTwo() > 0) {
                    String sql = "DELETE FROM golpartida WHERE idpartida = ' " + objs.get(i).getId() + " ';";

                    conexao.execSQL(sql);
                }
            }

            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método deleteAll: " + e.getMessage());
        }
    }

    public void reset() {
        String sql = "DELETE FROM golpartida ;";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método reset: " + e.getMessage());
        }
    }

    public List<GolPartida> findAll(Partida partida){
        String sql = "SELECT * FROM golpartida WHERE idpartida = '" + partida.getId() + "';";

        ArrayList<GolPartida> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql, null);
            //Instancia a lista
            objs = new ArrayList<>();
            //preenche a lista
            while (cursor.moveToNext()){
                objs.add(preencheObj(cursor));
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método findAll: "+ e.getMessage());
        }

        return objs;
    }

    public GolPartida find(int id){
        String sql = "SELECT * FROM golpartida WHERE idgolpartida = '" + id + "';";

        GolPartida obj = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);

            //preenche a lista
            if (cursor.moveToNext()){
                obj = preencheObj(cursor);
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método find: "+ e.getMessage());
        }

        return obj;
    }

    private GolPartida preencheObj(Cursor cursor){
        GolPartida obj = new GolPartida();

        obj.setId(cursor.getInt(cursor.getColumnIndex("idgolpartida")));
        obj.setIdPartida(cursor.getInt(cursor.getColumnIndex("idpartida")));
        obj.setGols(cursor.getInt(cursor.getColumnIndex("gols")));

        Player player = new PlayerDAO(this.context).find(cursor.getInt(cursor.getColumnIndex("idplayer")));
        obj.setPlayer(player);

        Jogador jogador = new JogadorDAO(this.context).find(cursor.getInt(cursor.getColumnIndex("idjogador")));
        obj.setJogador(jogador);

        return obj;
    }



}
