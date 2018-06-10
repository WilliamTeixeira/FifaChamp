package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Rodada;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class RodadaDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public RodadaDAO (Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public void delete(Rodada obj){
        String sql = "DELETE FROM partida WHERE idrodada = ' " + obj.getId() + " ';";

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

    public List<Rodada> findAll(){
        String sql = "SELECT idrodada, count(idpartida) as partidas, sum(golsone) + sum(golstwo) as gols FROM partida group by idrodada;";

        ArrayList<Rodada> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);
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

    public Rodada find(int id){

        Rodada obj = null;

        ArrayList<Partida> partidas = (ArrayList<Partida>) new PartidaDAO(this.context).findAll(id);

        if(partidas.size() > 0){
            obj = new Rodada();
            obj.setId(id);
            obj.setPartidas(partidas);
            obj.setTotalPartidas(partidas.size());
        }

        return obj;
    }

    public int lastId(){
        String sql = "SELECT max(idrodada) as lastid FROM partida;";
        int lastid = -1;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);
            //preenche a lista
            if (cursor.moveToNext()){
                lastid = cursor.getInt(cursor.getColumnIndex("lastid"));
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método findAll: "+ e.getMessage());
        }

        if(lastid == -1)
            lastid = 0;

        return lastid;
    }

    //traz apenas dados básicos
    private Rodada preencheObj(Cursor cursor){
        Rodada obj = new Rodada();
        obj.setId(cursor.getInt(cursor.getColumnIndex("idrodada")));
        obj.setTotalPartidas(cursor.getInt(cursor.getColumnIndex("partidas")));
        obj.setTotalGols(cursor.getInt(cursor.getColumnIndex("gols")));

        return obj;
    }
}
