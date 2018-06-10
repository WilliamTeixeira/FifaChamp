package com.wtx.fifachamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.PartidaDAO;
import com.wtx.fifachamp.DAO.PlayerDAO;
import com.wtx.fifachamp.DAO.RodadaDAO;
import com.wtx.fifachamp.adapter.ItemGeracaoAdapter;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Rodada;

import java.util.ArrayList;

public class GeracaoRodadaActivity extends AppCompatActivity {

    public static final int TELA_INSERT_RODADA = 1;

    ArrayList<Player> objs = null;
    ArrayList<Player> objsSelected = new ArrayList<>();

    int selecionados = 0;

    FloatingActionButton fabGerar;
    ListView lstPlayesRodada;
    TextView tvSelecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geracao_rodada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        buscaObjs();

        if (objs != null){
            preencheLstPlayers();
        }else {
            Toast.makeText(getApplicationContext(),"Não existem Player cadastrados.\n Insira-os!",Toast.LENGTH_LONG).show();
        }

        fabGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selecionados > 0 && selecionados % 2 == 0){
                    int idRodada = new RodadaDAO(getApplicationContext()).lastId() + 1;

                    Rodada rodada = new Rodada(idRodada, objsSelected);
                    new PartidaDAO(getApplicationContext()).insertAll(rodada);

                    rodada = new RodadaDAO(getApplicationContext()).find(rodada.getId());

                    Intent itn = new Intent(getApplicationContext(), PartidasActivity.class);
                    itn.putExtra("rodada", rodada);

                    PartidasActivity.GERANDO = true;
                    startActivityForResult(itn, TELA_INSERT_RODADA);

                    Snackbar.make(view, "Gerando a rodada ... ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else {
                    Snackbar.make(view, "Selecione um numero par de jogadores ... ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
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

    private void preencheLstPlayers() {

        ItemGeracaoAdapter adapter = new ItemGeracaoAdapter(getApplicationContext(), objs);

        lstPlayesRodada.setAdapter(adapter);

        lstPlayesRodada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(objsSelected.contains(objs.get(position))){
                    objsSelected.remove(objs.get(position));
                    selecionados--;
                    tvSelecionados.setText("Selecionados: "+String.valueOf(selecionados));
                }else{
                    objsSelected.add(objs.get(position));
                    selecionados++;
                    tvSelecionados.setText("Selecionados: "+String.valueOf(selecionados));
                }
            }
        });

    }

    private void buscaObjs() {
        objs = (ArrayList<Player>) new PlayerDAO(getApplicationContext()).findAll();
    }

    private void binding() {
        tvSelecionados = findViewById(R.id.tvSelecionados);
        lstPlayesRodada = findViewById(R.id.lstPlayesRodada);
        lstPlayesRodada.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        fabGerar = (FloatingActionButton) findViewById(R.id.fabGerar);

    }

}
