package com.wtx.fifachamp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by willi on 16/04/2018.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final String dataBaseName = "fifawtx.db";
    public static final int dataBaseVersion = 2;

    public DataBase(Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
    }

    String sqlCreateTeam = "CREATE TABLE team (idteam INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " nome TEXT);";

    String sqlCreatePlayer = "CREATE TABLE player (idplayer INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " nome TEXT, " +
            " idteam INTEGER);";

    String sqlCreateJogador = "CREATE TABLE jogador (idjogador INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " nome TEXT, " +
            " idteam INTEGER);";

    String sqlCreatePartida = "CREATE TABLE partida (idpartida INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " idrodada INTEGER NOT NULL, " +
            " idplayerone INTEGER NOT NULL, " +
            " golsone INTEGER, " +
            " idplayertwo INTEGER NOT NULL, " +
            " golstwo INTEGER);";

    String sqlCreateGolPartida = "CREATE TABLE golpartida (idgolpartida INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " idpartida INTEGER NOT NULL, " +
            " idplayer INTEGER NOT NULL, " +
            " idjogador INTEGER NOT NULL, " +
            " gols INTEGER);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTeam);
        db.execSQL(sqlCreateJogador);
        db.execSQL(sqlCreatePlayer);
        db.execSQL(sqlCreatePartida);
        db.execSQL(sqlCreateGolPartida);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE team");
        db.execSQL("DROP TABLE jogador");
        db.execSQL("DROP TABLE player");
      //  db.execSQL("DROP TABLE partida");
      //  db.execSQL("DROP TABLE golpartida");

        onCreate(db);
    }
}
