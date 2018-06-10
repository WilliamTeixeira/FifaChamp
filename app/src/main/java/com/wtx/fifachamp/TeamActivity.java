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
import com.wtx.fifachamp.DAO.TeamDAO;
import com.wtx.fifachamp.adapter.TeamAdapter;
import com.wtx.fifachamp.model.Team;

import java.util.List;

public class TeamActivity extends AppCompatActivity {

    public static final int TELA_INSERT_TEAM = 1;
    public static final int TELA_DETAIL_TEAM = 2;
    private List<Team> objs = null;

    ListView list;
    FloatingActionButton fabIncluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        buscaObjs();

        if (objs != null){
            preencheListView();
        }else {
            Toast.makeText(getApplicationContext(),"Não existem Times cadastrados.\n Insira os Times!",Toast.LENGTH_LONG).show();
        }


        fabIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Incluir", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent itn = new Intent(getApplicationContext(), TeamCadastroActivity.class);
                TeamCadastroActivity.INSERINDO = true;

                startActivityForResult(itn, TELA_INSERT_TEAM);
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

        if(idItem == R.id.menuSair) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    private void preencheListView() {
        //montando a tela somente se a consulta do banco for diferente de null
        TeamAdapter teamAdapter = new TeamAdapter(getApplicationContext(), objs);

        list.setAdapter(teamAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itn = new Intent(getApplicationContext(), TeamCadastroActivity.class);
                itn.putExtra("team",objs.get(position));
                startActivityForResult(itn,TELA_DETAIL_TEAM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TELA_INSERT_TEAM || requestCode == TELA_DETAIL_TEAM){
            objs = null;
            buscaObjs();
            preencheListView();
        }
    }

    private void buscaObjs() {
        objs = new TeamDAO(getApplicationContext()).findAll();
    }

    private void binding() {
        fabIncluir = (FloatingActionButton) findViewById(R.id.fabIncluirTeam);
        list = findViewById(R.id.lstTeam);
    }

}
