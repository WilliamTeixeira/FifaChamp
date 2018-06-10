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
import android.widget.ListView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.PlayerDAO;
import com.wtx.fifachamp.adapter.PlayerAdapter;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Team;

import java.util.List;

public class PlayersActivity extends AppCompatActivity {

    public static final int TELA_INSERT_PLAYER = 1;
    public static final int TELA_DETAIL_PLAYER = 2;
    private List<Player> objs = null;

    FloatingActionButton fabIncluir;
    ListView lstPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        lstPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itn = new Intent(getApplicationContext(), PlayerCadastroActivity.class);
                itn.putExtra("player", objs.get(position));
                PlayerCadastroActivity.INSERINDO = false;
                startActivityForResult(itn, TELA_DETAIL_PLAYER);
            }
        });

        fabIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Incluir", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent itn = new Intent(getApplicationContext(), PlayerCadastroActivity.class);
                PlayerCadastroActivity.INSERINDO = true;
                startActivityForResult(itn, TELA_INSERT_PLAYER);
            }
        });


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

        if(idItem == R.id.menuSair)
            finish();

        return super.onOptionsItemSelected(item);
    }
    //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        objs = null;
        lstPlayers.setAdapter(null);
        preencheDados();
    }

    private void preencheDados() {
        objs = new PlayerDAO(getApplicationContext()).findAll();

        if(objs.size() > 0){
            PlayerAdapter adapter = new PlayerAdapter(getApplicationContext(), objs);
            lstPlayers.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(),"Não existem usuários cadastrados!", Toast.LENGTH_LONG).show();
        }
    }

    private void binding() {
        fabIncluir = (FloatingActionButton) findViewById(R.id.fabIncluirPlayer);
        lstPlayers = findViewById(R.id.lstPlayers);
    }

}
