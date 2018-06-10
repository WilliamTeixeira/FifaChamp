package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wtx.fifachamp.model.Jogador;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 16/04/2018.
 */

public class TeamDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public TeamDAO(Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public void insert(Team obj){

        String sql = "INSERT INTO team (nome) VALUES ('" + obj.getNome() + "')";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método insert: " + e.getMessage());
        }

    }

    public void update(Team obj){
        String sql = "UPDATE team SET nome = '" + obj.getNome() + "' WHERE idteam = '" + obj.getId() + "';";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método update: " + e.getMessage());
        }
    }

    public void delete(Team obj){
        String sql = "DELETE FROM team WHERE idteam = ' " + obj.getId() + " ';";

        try {
            //Abre a conexao
            open();
            //Executa a query
            conexao.execSQL(sql);
            close();
        }catch (SQLException e){
            Log.i("ERROR", "Erro método delete: " + e.getMessage());
        }
    }
    public boolean isUsed(int id){
        String sql =   "SELECT count(*) qtdplayer FROM player WHERE idteam = '" + id + "';";

        int qtdplayer = 0;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);

            //preenche a lista
            if (cursor.moveToNext()){
                qtdplayer = cursor.getInt(cursor.getColumnIndex("qtdplayer"));
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método find: "+ e.getMessage());
        }

        if(qtdplayer > 0)
            return true;
        else
            return false;
    }
    public List<Team> findAll(){
        String sql = "select * from team;";
        String[] campos = {"idteam", "nome"};

        ArrayList<Team> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.query("team",campos,null,null,null,null,null);
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

    public Team find(int id){
        String sql = "select * from team where idteam = '" + id + " ';";

        Team obj = null;

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

    public int lastId(){
        String sql = "SELECT * from SQLITE_SEQUENCE where name = 'team';";

        int lastId = 0;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.rawQuery(sql,null);

            //preenche a lista
            if (cursor.moveToNext()){
                lastId = cursor.getInt(cursor.getColumnIndex("seq"));
            }
            //Fecha a conexao
            close();

        }catch (SQLException e){
            Log.i( "ERROR", "Erro método lastid: "+ e.getMessage());
        }

        return lastId;
    }

    private Team preencheObj(Cursor cursor){
        Team obj = new Team();
        obj.setId(cursor.getInt(cursor.getColumnIndex("idteam")));
        obj.setNome(cursor.getString(cursor.getColumnIndex("nome")));

        //utiliza a consulta da dao jogadores
        ArrayList<Jogador> jogadores = (ArrayList<Jogador>) new JogadorDAO(this.context).findAllByTeam(obj.getId());

        obj.setJogadores(jogadores);

        return obj;
    }
}
