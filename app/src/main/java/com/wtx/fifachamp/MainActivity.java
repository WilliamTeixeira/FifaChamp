package com.wtx.fifachamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.GolPartidaDAO;
import com.wtx.fifachamp.DAO.PartidaDAO;
import com.wtx.fifachamp.DAO.PlayerScoreDAO;
import com.wtx.fifachamp.DAO.RankingDAO;
import com.wtx.fifachamp.DAO.RodadaDAO;
import com.wtx.fifachamp.adapter.RodadaAdapter;
import com.wtx.fifachamp.model.Ranking;
import com.wtx.fifachamp.model.Rodada;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fabGerarRodada;
    ListView lstRodada;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    public static final int TELA_DETAIL_RODADA = 1;
    public static final int TELA_GERAR_RODADA = 4;
    public static final int TELA_PLAYER = 2;
    public static final int TELA_TEAM = 3;

    private List<Rodada> objs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        fabGerarRodada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Gerar rodada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent itn = new Intent(getApplicationContext(), GeracaoRodadaActivity.class);
                startActivityForResult(itn,TELA_GERAR_RODADA );
            }
        });

        lstRodada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itn = new Intent(getApplicationContext(), PartidasActivity.class);
                itn.putExtra("rodada", objs.get(position));
                PartidasActivity.GERANDO = false;
                startActivityForResult(itn, TELA_DETAIL_RODADA);
            }
        });

        //nav view
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != 99) {
            objs = null;
            lstRodada.setAdapter(null);
            preencheDados();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_player) {
            Intent itn = new Intent(getApplicationContext(), PlayersActivity.class);
            startActivityForResult(itn, TELA_PLAYER);
        } else if (id == R.id.nav_team) {
            Intent itn = new Intent(getApplicationContext(), TeamActivity.class);
            startActivityForResult(itn, TELA_TEAM);
        } else if (id == R.id.nav_ranking) {
            Ranking r = new RankingDAO(getApplicationContext()).find();
            if(r != null) {
                Intent itn = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(itn);
            }else{
                Toast.makeText(getApplicationContext(),"Não existem partidas para gerar um ranking", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_manage) {
            new PartidaDAO(getApplicationContext()).reset();
            new GolPartidaDAO(getApplicationContext()).reset();
            onActivityResult(88,0,new Intent());
            Toast.makeText(getApplicationContext(),"Resentando Partidas e Gols ...",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void preencheDados() {
        objs = new RodadaDAO(getApplicationContext()).findAll();

        if(objs.size() > 0){
            RodadaAdapter adapter = new RodadaAdapter(getApplicationContext(),objs);
            lstRodada.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(),"Não existem rodadas cadastradas!", Toast.LENGTH_LONG).show();
        }
    }

    private void binding() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        lstRodada= findViewById(R.id.lstRodadas);
        fabGerarRodada = (FloatingActionButton) findViewById(R.id.fabGerarRodada);
    }
}
