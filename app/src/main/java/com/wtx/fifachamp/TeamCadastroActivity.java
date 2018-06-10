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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.PlayerDAO;
import com.wtx.fifachamp.DAO.TeamDAO;
import com.wtx.fifachamp.adapter.JogadorAdapter;
import com.wtx.fifachamp.model.Jogador;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;

public class TeamCadastroActivity extends AppCompatActivity {

    public static final int TELA_INSERT_JOGADOR = 3;
    public static final int TELA_DETAIL_JOGADOR = 4;
    public static boolean INSERINDO = false;

    EditText edtNomeTime;
    ListView list;
    Team team;
    FloatingActionButton fabSalvar, fabIncluiJogador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();

        preencheDados();

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salvando", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                team.setNome(edtNomeTime.getText().toString());

                //salva somente se existem jogadores setados para o team
                if(team.getJogadores().size() == 0 ){
                    Toast.makeText(
                            getApplicationContext(),
                            "Não existem jogadores para este time.\nInsira ao menos um jogador!",
                            Toast.LENGTH_LONG).show();
                }else {
                    new TeamDAO(getApplicationContext()).update(team);
                    finish();
                }

            }
        });


        fabIncluiJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Incluir Jogador", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //atualizo o obj ou o banco
                team.setNome(edtNomeTime.getText().toString());
                if(INSERINDO){
                    team.setId(new TeamDAO(getApplicationContext()).lastId() + 1);
                }else {
                    new TeamDAO(getApplicationContext()).update(team);
                }

                //passo pra tela de cadastro de jogador
                Intent itn = new Intent(getApplicationContext(), JogadorCadastroActivity.class);
                itn.putExtra("team",team);
                JogadorCadastroActivity.INSERINDO = true;
                startActivityForResult(itn,TELA_INSERT_JOGADOR);

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

        if(idItem == R.id.menuExcluir){
            if(INSERINDO) {
                Toast.makeText(getApplicationContext(), "Não é possivel excluir pois o registro ainda não existe!", Toast.LENGTH_LONG).show();
            }else {
                boolean usado = new TeamDAO(getApplicationContext()).isUsed(team.getId());

                if (team.getJogadores().size() == 0 && !usado) {
                    new TeamDAO(getApplicationContext()).delete(team);
                    Toast.makeText(getApplicationContext(), "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Existem jogadores presentes para este Time\n" +
                            "ou existem Players utilizando eeste Time.\n Remova os vinculos antes de deletar", Toast.LENGTH_LONG).show();
                }
            }
        }

        if(idItem == R.id.menuSair) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    public void preencheDados() {
        if (INSERINDO){
            team = new Team();
            preencheListView(false);
        }else{
            if(team != null){
                team = new TeamDAO(getApplicationContext()).find(team.getId());
                edtNomeTime.setText(team.getNome());
                preencheListView(true);
            }else {
                team = (Team) getIntent().getExtras().get("team");
                edtNomeTime.setText(team.getNome());
                preencheListView(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TELA_INSERT_JOGADOR || requestCode == TELA_DETAIL_JOGADOR){
            list.setAdapter(null);
            preencheDados();
        }
    }

    private void preencheListView(boolean temDados) {
        if (temDados) {
            if (team.getJogadores().size() > 0) {
                JogadorAdapter jogadorAdapter = new JogadorAdapter(getApplicationContext(), team.getJogadores());
                list.setAdapter(jogadorAdapter);

                //monta o onItemClick da listview
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent itn = new Intent(getApplicationContext(), JogadorCadastroActivity.class);
                        itn.putExtra("jogador", team.getJogadores().get(position));
                        JogadorCadastroActivity.INSERINDO = false;
                        startActivityForResult(itn, TELA_DETAIL_JOGADOR);
                    }
                });

            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Não existem jogadores para este time.\nInsira ao menos um jogador!",
                        Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    "Não existem jogadores para este time.\nInsira ao menos um jogador!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void binding() {
        fabSalvar = (FloatingActionButton) findViewById(R.id.fabSalvarTeam);
        fabIncluiJogador = (FloatingActionButton) findViewById(R.id.fabJogador);
        edtNomeTime =findViewById(R.id.edtNomeTime);
        list = findViewById(R.id.lstJogadores);
    }

}
