package com.wtx.fifachamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.PlayerScoreDAO;
import com.wtx.fifachamp.DAO.RankingDAO;
import com.wtx.fifachamp.adapter.PlayerScoreAdapter;
import com.wtx.fifachamp.adapter.RodadaAdapter;
import com.wtx.fifachamp.model.PlayerScore;
import com.wtx.fifachamp.model.PlayerScoreCompare;
import com.wtx.fifachamp.model.Ranking;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    TextView tvNumRodadas, tvNumParidas, tvArtilheiroPlayer, tvArtilheiroNome;
    ListView lstRanking;
    List<PlayerScore> objs = null;
    Ranking obj = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setTitle(R.string.title_activity_ranking);
        binding();
        preencheDados();

    }

    //region Menu Sair
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sair, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();

        if(idItem == R.id.menuSair) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    private void preencheDados() {
        obj = new RankingDAO(getApplicationContext()).find();

        tvNumRodadas.setText("Total de Rodadas: " + String.valueOf(obj.getNumRodadas()));
        tvNumParidas.setText("Total de Partidas: " + String.valueOf(obj.getNumPartidas()));
        tvArtilheiroNome.setText(obj.getArtilheiro().getNome() + " | " + String.valueOf(obj.getGolsArtilheiro()) + " Gols");
        tvArtilheiroPlayer.setText(obj.getPlayerArtilheiro().getNome() + " | " + obj.getPlayerArtilheiro().getTeam().getNome());

        objs = obj.getPlayerScores();
        if(objs.size() > 0){
            PlayerScoreAdapter adapter = new PlayerScoreAdapter(getApplicationContext(),objs);
            lstRanking.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(),"Não existem rodadas cadastradas!", Toast.LENGTH_LONG).show();
        }
    }

    private void binding() {
        tvNumRodadas = findViewById(R.id.tvNumRodadas);
        tvNumParidas = findViewById(R.id.tvNumParidas);
        tvArtilheiroPlayer = findViewById(R.id.tvArtlheiroPlayer);
        tvArtilheiroNome = findViewById(R.id.tvArtilheiroNome);

        lstRanking = findViewById(R.id.lstRanking);
    }
}
