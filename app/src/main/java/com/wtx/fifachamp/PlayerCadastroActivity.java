package com.wtx.fifachamp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wtx.fifachamp.DAO.PlayerDAO;
import com.wtx.fifachamp.DAO.TeamDAO;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerCadastroActivity extends AppCompatActivity {

    public static boolean INSERINDO = true;
    Player obj = null;
    List<Team> teams = null;
    ArrayAdapter<String> adapter = null;

    FloatingActionButton fabSalvar;
    EditText edtNome;
    Spinner spnTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salvar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if (spnTeam.getSelectedItemPosition() == 0 || edtNome.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Faltam dado a serem preenchidos", Toast.LENGTH_LONG).show();
                }else {
                    obj.setNome(edtNome.getText().toString());
                    obj.setTeam(teams.get(spnTeam.getSelectedItemPosition() - 1));

                    if(INSERINDO)
                        new PlayerDAO(getApplicationContext()).insert(obj);
                    else
                        new PlayerDAO(getApplicationContext()).update(obj);

                    Toast.makeText(getApplicationContext(),"Registro salvo com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    private void preencheDados() {
        if(INSERINDO) {
            obj = new Player();
        }else {
            obj = (Player) getIntent().getExtras().get("player");
            edtNome.setText(obj.getNome());
        }
        teams = new TeamDAO(getApplicationContext()).findAll();

        preencheSpn(teams);
    }

    private void preencheSpn(List<Team> teams) {
        String[] listaSpn;
        int position = 0;

        if(teams.size() > 0) {
            listaSpn = new String[teams.size() + 1];
            listaSpn[0] = "Selecione um Time ...";
            int idx = 1;

            for (Team t : teams) {
                if(!INSERINDO && t.getId() == obj.getTeam().getId())
                    position = idx;

                listaSpn[idx++] = t.getNome();
            }

        }else {
            listaSpn = new String[1];
            listaSpn[0] = "Não há Times ...";
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaSpn);

        spnTeam.setAdapter(adapter);

        if (!INSERINDO && teams.size() > 0) {
            spnTeam.setSelection(position);
            spnTeam.setEnabled(false);
        }

    }

    private void binding() {
        fabSalvar = (FloatingActionButton) findViewById(R.id.fabSalvarPlayer);
        edtNome = findViewById(R.id.edtNome);
        spnTeam = findViewById(R.id.spnTeam);
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

                boolean usado = new PlayerDAO(getApplicationContext()).isUsed(obj.getId());

                if(usado) {
                    Toast.makeText(getApplicationContext(), "Existem partidas para este player, não é possível excluilo!\n" +
                            "Resete a base de dados para permitir excluir", Toast.LENGTH_LONG).show();
                }else {
                    new PlayerDAO(getApplicationContext()).delete(obj);
                    Toast.makeText(getApplicationContext(), "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }

        if(idItem == R.id.menuSair) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

}
