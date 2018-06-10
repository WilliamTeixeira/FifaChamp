package com.wtx.fifachamp;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.GolPartidaDAO;
import com.wtx.fifachamp.DAO.PartidaDAO;
import com.wtx.fifachamp.DAO.PlayerDAO;
import com.wtx.fifachamp.DAO.TeamDAO;
import com.wtx.fifachamp.adapter.GolsPartidaAdapter;
import com.wtx.fifachamp.adapter.JogadorAdapter;
import com.wtx.fifachamp.model.GolPartida;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Team;

import java.util.List;

public class PartidaCadastroActivity extends AppCompatActivity {

    public static boolean ATUALIZAR = false;
    public static boolean INSERINDO = false;

    public static final int TELA_INSERIR_GOL = 1;
    public static final int TELA_DETAIL_GOL = 2;

    private Partida obj = null;
    private List<GolPartida> objs = null;

    TextView tvNomePlayerOne, tvNomeTimePlayerOne, tvGolsPlayerOne, tvNomePlayerTwo, tvNomeTimePlayerTwo, tvGolsPlayerTwo;
    ListView lstGolsPartida;
    FloatingActionButton fabGolOne, fabGolTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        fabGolOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Inserir Gols " + obj.getPlayerOne().getNome(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent itn = new Intent(getApplicationContext(), GolsCadastroActivity.class);
                itn.putExtra("partida", obj);
                itn.putExtra("player", obj.getPlayerOne());
                GolsCadastroActivity.INSERINDO = true;
                startActivityForResult(itn, TELA_INSERIR_GOL);
            }
        });

        fabGolTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Inserir Gols" + obj.getPlayerTwo().getNome(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent itn = new Intent(getApplicationContext(), GolsCadastroActivity.class);
                itn.putExtra("partida", obj);
                itn.putExtra("player", obj.getPlayerTwo());
                GolsCadastroActivity.INSERINDO = true;
                startActivityForResult(itn, TELA_INSERIR_GOL);
            }
        });

        lstGolsPartida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itn = new Intent(getApplicationContext(), GolsCadastroActivity.class);
                itn.putExtra("golpartida", objs.get(position));
                itn.putExtra("partida", obj);
                GolsCadastroActivity.INSERINDO = false;
                startActivityForResult(itn, TELA_DETAIL_GOL);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        lstGolsPartida.setAdapter(null);
        objs = null;
        ATUALIZAR = true;
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
        //partida
        if(ATUALIZAR) {
            int id = obj.getId();
            obj = null;
            obj = new PartidaDAO(getApplicationContext()).find(id);
            ATUALIZAR = false;
        } else {
            obj = (Partida) getIntent().getExtras().get("partida");
        }
        //gols da partida
        objs = new GolPartidaDAO(getApplicationContext()).findAll(obj);

        tvNomePlayerOne.setText(obj.getPlayerOne().getNome());
        tvNomeTimePlayerOne.setText("Time: " + obj.getPlayerOne().getTeam().getNome());
        tvGolsPlayerOne.setText("Gols: " + String.valueOf(obj.getGolsOne()));

        tvNomePlayerTwo.setText(obj.getPlayerTwo().getNome());
        tvNomeTimePlayerTwo.setText("Time: " + obj.getPlayerTwo().getTeam().getNome());
        tvGolsPlayerTwo.setText("Gols: " + String.valueOf(obj.getGolsTwo()));


        preencheListView();

    }

    private void preencheListView() {
        if (obj.getGolPartidas().size() > 0) {
            GolsPartidaAdapter adapter = new GolsPartidaAdapter(getApplicationContext(), objs);
            lstGolsPartida.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(),"Não existem gols cadastrados para essa partida!", Toast.LENGTH_LONG).show();
        }

    }

    private void binding() {
        fabGolOne = (FloatingActionButton) findViewById(R.id.fabGolOne);
        fabGolTwo = (FloatingActionButton) findViewById(R.id.fabGolTwo);

        tvNomePlayerOne = findViewById(R.id.tvNomePOne);
        tvNomeTimePlayerOne = findViewById(R.id.tvNomeTimePOne);
        tvGolsPlayerOne = findViewById(R.id.tvGolsPOne);

        tvNomePlayerTwo = findViewById(R.id.tvNomePTwo);
        tvNomeTimePlayerTwo = findViewById(R.id.tvNomeTimePTwo);
        tvGolsPlayerTwo = findViewById(R.id.tvGolsPTwo);

        lstGolsPartida = findViewById(R.id.lstGolsPartida);
    }

}
