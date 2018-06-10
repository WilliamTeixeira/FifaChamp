package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 16/04/2018.
 */

public class PlayerDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public PlayerDAO(Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public void insert(Player obj){

        String sql = "INSERT INTO player (nome, idteam) VALUES ('" + obj.getNome() + "', '" + obj.getTeam().getId() + "')";

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

    public void update(Player obj){
        String sql = "UPDATE player SET nome = '" + obj.getNome() + "', idteam = '" + obj.getTeam().getId() + "' WHERE idplayer = '" + obj.getId() + "';";

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

    public void delete(Player obj){
        String sql = "DELETE FROM player WHERE idplayer = ' " + obj.getId() + " ';";

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

    public List<Player> findAll(){
        String sql = "select * from player;";
        String[] campos = {"idplayer", "nome", "idteam"};

        ArrayList<Player> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.query("player",campos,null,null,null,null,null);
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

    public Player find(int id){
        String sql = "select * from player where idplayer = '" + id + " ';";

        Player obj = null;

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

    public boolean isUsed(int id){
        String sql =   "SELECT count(*) qtdjogos FROM partida WHERE idplayerone = '" + id +
                               "' OR idplayertwo = '" + id + "';";
        int qtdJogos = 0;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);

            //preenche a lista
            if (cursor.moveToNext()){
                qtdJogos = cursor.getInt(cursor.getColumnIndex("qtdjogos"));
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método find: "+ e.getMessage());
        }

        if(qtdJogos > 0)
            return true;
        else
            return false;
    }

    private Player preencheObj(Cursor cursor){
        Player obj = new Player();
        obj.setId(cursor.getInt(cursor.getColumnIndex("idplayer")));
        obj.setNome(cursor.getString(cursor.getColumnIndex("nome")));

        //Utilizo a consulta da TeamDAO
        Team team = new TeamDAO(this.context).find(cursor.getInt(cursor.getColumnIndex("idteam")));
        obj.setTeam(team);

        return obj;
    }

}
