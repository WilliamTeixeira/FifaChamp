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

public class JogadorDAO {

    private SQLiteDatabase conexao;
    private DataBase banco;
    private Context context;

    public JogadorDAO(Context context) {
        banco = new DataBase(context);
        this.context = context;
    }

    private void open(){
        conexao = banco.getWritableDatabase();
    }

    private void close(){
        conexao.close();
    }

    public void insert(Jogador obj){

        String sql = "INSERT INTO jogador (nome, idteam) VALUES ('" + obj.getNome() + "', '" + obj.getIdTeam() + "')";

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

    public void update(Jogador obj){
        String sql = "UPDATE jogador SET nome = '" + obj.getNome() + "', idteam = '" + obj.getIdTeam() + "' WHERE idjogador = '" + obj.getId() + "';";

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

    public void delete(Jogador obj){
        String sql = "DELETE FROM jogador WHERE idjogador = ' " + obj.getId() + " ';";

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
        String sql =   "SELECT count(*) qtdjogos FROM golpartida WHERE idjogador = '" + id + "';";
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

    public List<Jogador> findAll(){
        String sql = "select * from jogador;";
        String[] campos = {"idjogador", "nome", "idteam"};

        ArrayList<Jogador> objs = null;

        try {
            //Abre a conexao
            open();
            //Preenche o cursor com a consulta
            Cursor cursor =  conexao.query("jogador",campos,null,null,null,null,null);
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

    public Jogador find(int id){
        String sql = "select * from jogador where idjogador = '" + id + " ';";

        Jogador obj = null;

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

    public List<Jogador> findAllByTeam(int idTeam){
        String sql = "select * from jogador where idteam = '" +idTeam +"';";

        ArrayList<Jogador> objs = null;

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

    private Jogador preencheObj(Cursor cursor){
        Jogador obj = new Jogador();
        obj.setId(cursor.getInt(cursor.getColumnIndex("idjogador")));
        obj.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        obj.setIdTeam(cursor.getInt(cursor.getColumnIndex("idteam")));

        return obj;
    }
}
