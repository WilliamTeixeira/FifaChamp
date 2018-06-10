package com.wtx.fifachamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.GolPartidaDAO;
import com.wtx.fifachamp.DAO.PartidaDAO;
import com.wtx.fifachamp.DAO.RodadaDAO;
import com.wtx.fifachamp.adapter.PartidaAdapter;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Rodada;

import java.util.ArrayList;
import java.util.List;

import static com.wtx.fifachamp.PlayersActivity.TELA_DETAIL_PLAYER;

public class PartidasActivity extends AppCompatActivity {

    public static final int TELA_DETAIL_PARTIDA = 1;
    public static boolean GERANDO = false;
    public static boolean ATUALIZAR = false;

    private List<Partida> objs = null;
    private Rodada obj = null;

    ListView lstPartidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        lstPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itn = new Intent(getApplicationContext(), PartidaCadastroActivity.class);
                itn.putExtra("partida", objs.get(position));
                PartidaCadastroActivity.INSERINDO = false;
                startActivityForResult(itn, TELA_DETAIL_PARTIDA);
            }
        });
    }

    //region Menu Sair
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deletar_sair, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();

        if(idItem == R.id.menuSair) {
            finish();
        }

        if (idItem == R.id.menuExcluir) {
            new PartidaDAO(getApplicationContext()).deleteAll(obj);
            new GolPartidaDAO(getApplicationContext()).deleteAll(obj.getPartidas());

            Toast.makeText(getApplicationContext(), "Registros excluidos com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        objs = null;
        lstPartidas.setAdapter(null);
        ATUALIZAR = true;
        preencheDados();
    }

    private void preencheDados() {
        if (ATUALIZAR) {
            int id = obj.getId();
            obj = null;
            obj = new RodadaDAO(getApplicationContext()).find(id);
            ATUALIZAR = false;
        }else {
            obj = (Rodada) getIntent().getExtras().get("rodada");
        }

        objs = new PartidaDAO(getApplicationContext()).findAll(obj.getId());
        obj.setPartidas((ArrayList<Partida>) objs);

        if(objs.size() > 0){
            PartidaAdapter adapter = new PartidaAdapter(getApplicationContext(), objs);
            lstPartidas.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(),"Não existem partidas cadastrados para essa rodada!", Toast.LENGTH_LONG).show();
        }
    }

    private void binding() {
        lstPartidas = findViewById(R.id.lstPartidas);
    }

}
