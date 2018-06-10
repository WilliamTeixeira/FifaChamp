package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.ContactsContract;
import android.util.Log;

import com.wtx.fifachamp.model.GolPartida;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Rodada;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public PartidaDAO(Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public void insert(Partida obj){

        String sql = "INSERT INTO partida (idrodada, idplayerone, golsone, idplayertwo, golstwo) " +
                     " VALUES ('" + obj.getIdRodada()           + "', '" +
                                    obj.getPlayerOne().getId()  + "', '" +
                                    obj.getGolsOne()            + "', '" +
                                    obj.getPlayerTwo().getId()  + "', '" +
                                    obj.getGolsTwo()            + "');";

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

    public  void  insertAll(Rodada rodada){

        ArrayList<Partida> objs = rodada.getPartidas();

        int i;
        String sql;

        try {
            //Abre a conexao
            open();

            //inicia a transação
            conexao.beginTransactionNonExclusive();

            //Executa a query
            for (i = 0; i < objs.size(); i++) {
                sql = "INSERT INTO partida (idrodada, idplayerone, golsone, idplayertwo, golstwo) " +
                        " VALUES ('"  + objs.get(i).getIdRodada()           + "', '" +
                        objs.get(i).getPlayerOne().getId()  + "', '" +
                        objs.get(i).getGolsOne()            + "', '" +
                        objs.get(i).getPlayerTwo().getId()  + "', '" +
                        objs.get(i).getGolsTwo()            + "');";

                conexao.execSQL(sql);
            }

            //finaliza a tranação
            conexao.setTransactionSuccessful();
            conexao.endTransaction();

            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método insert: " + e.getMessage());
        }

    }

    public void update(Partida obj){
        String sql =   "UPDATE partida SET " +
                        " idrodada = '" + obj.getIdRodada() +
                        "', idplayerone = '" + obj.getPlayerOne().getId() +
                        "', golsone = '" + obj.getGolsOne() +
                        "', idplayertwo = '" + obj.getPlayerTwo().getId() +
                        "', golstwo = '" + obj.getGolsTwo() +
                        "'  WHERE idpartida = '" + obj.getId() +
                        "';";

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

    public void delete(Partida obj){
        String sql = "DELETE FROM partida WHERE idpartida = ' " + obj.getId() + " ';";

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

    public void deleteAll(Rodada obj) {
        String sql = "DELETE FROM partida WHERE idrodada = ' " + obj.getId() + " ';";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            //Fecha a conexao
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método deleteAll: " + e.getMessage());
        }
    }

    public void reset() {
        String sql = "DELETE FROM partida ;";

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

    public List<Partida> findAll(int idRodada){
        String sql = "SELECT * FROM partida WHERE idrodada = '" + idRodada + "';";

        ArrayList<Partida> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);
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

    public List<Partida> findAllByPlayer(int idPlayer){
        String sql = "SELECT * FROM partida WHERE idplayerone =  '" + idPlayer + "' OR idplayertwo = '" + idPlayer + "';";

        ArrayList<Partida> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);
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
    public Partida find(int id){
        String sql = "SELECT * FROM partida WHERE idpartida = '" + id + "';";

        Partida obj = null;

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


    private Partida preencheObj(Cursor cursor){
        Partida obj = new Partida();
        obj.setId(cursor.getInt(cursor.getColumnIndex("idpartida")));
        obj.setIdRodada(cursor.getInt(cursor.getColumnIndex("idrodada")));

        Player p1 = new PlayerDAO(this.context).find(cursor.getInt(cursor.getColumnIndex("idplayerone")));
        obj.setPlayerOne(p1);
        obj.setGolsOne(cursor.getInt(cursor.getColumnIndex("golsone")));

        Player p2 = new PlayerDAO(this.context).find(cursor.getInt(cursor.getColumnIndex("idplayertwo")));
        obj.setPlayerTwo(p2);
        obj.setGolsTwo(cursor.getInt(cursor.getColumnIndex("golstwo")));

        //recuperar da GolsPartidaDAO
        ArrayList<GolPartida> golsPartidas = (ArrayList<GolPartida>) new GolPartidaDAO(this.context).findAll(obj);
        obj.setGolPartidas(golsPartidas);

        //Atualiza os pontos da partida
        obj.atualizaPts();

        return obj;
    }



}
