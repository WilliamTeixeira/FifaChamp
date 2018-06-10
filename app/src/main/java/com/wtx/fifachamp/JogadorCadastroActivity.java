package com.wtx.fifachamp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.JogadorDAO;
import com.wtx.fifachamp.DAO.TeamDAO;
import com.wtx.fifachamp.model.Jogador;
import com.wtx.fifachamp.model.Team;

public class JogadorCadastroActivity extends AppCompatActivity {

    public static boolean INSERINDO = false;

    FloatingActionButton fabSalvar;
    EditText edtNomeJogador;

    Jogador jogador;
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogador_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Salvando", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Atualiza o objeto
                jogador.setNome(edtNomeJogador.getText().toString());

                //Insere ou salva no banco
                if (edtNomeJogador.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Faltam dados a serem preenhidos", Toast.LENGTH_LONG).show();
                }else{
                    if (INSERINDO) {
                        if(TeamCadastroActivity.INSERINDO) {
                            new TeamDAO(getApplicationContext()).insert(team);
                            TeamCadastroActivity.INSERINDO = false;
                        }
                        new JogadorDAO(getApplicationContext()).insert(jogador);
                    }else {
                        new JogadorDAO(getApplicationContext()).update(jogador);
                    }
                    Toast.makeText(getApplicationContext(), "Salvo com sucesso", Toast.LENGTH_LONG).show();
                    TeamCadastroActivity.INSERINDO = false;
                    finish();
                }
                //informa que não se trata mais de uma inclusao de time

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
            if (INSERINDO) {
                Toast.makeText(getApplicationContext(), "Jogador não foi salvo!", Toast.LENGTH_LONG).show();
            }else {
                boolean usado = new JogadorDAO(getApplicationContext()).isUsed(jogador.getId());
                if(usado){
                    Toast.makeText(getApplicationContext(), "Jogador esta sendo utilizado em partidas.\n" +
                            "Para excluir, limpe a base de dados!", Toast.LENGTH_LONG).show();
                }else {
                    new JogadorDAO(getApplicationContext()).delete(jogador);
                    Toast.makeText(getApplicationContext(), "Registro excluido com sucesso", Toast.LENGTH_LONG).show();
                }
            }
            finish();
        }

        if(idItem == R.id.menuSair) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    private void preencheDados() {
        if (INSERINDO){
            team = (Team) getIntent().getExtras().get("team");
            jogador = new Jogador();
            jogador.setIdTeam(team.getId());
        }else {
            jogador = (Jogador) getIntent().getExtras().get("jogador");
            edtNomeJogador.setText(jogador.getNome());
        }
    }

    private void binding() {
        fabSalvar = (FloatingActionButton) findViewById(R.id.fabSalvarJogador);
        edtNomeJogador = findViewById(R.id.edtNomeJogador);
    }

}
