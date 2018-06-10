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

import com.wtx.fifachamp.DAO.GolPartidaDAO;
import com.wtx.fifachamp.DAO.JogadorDAO;
import com.wtx.fifachamp.DAO.PartidaDAO;
import com.wtx.fifachamp.DAO.PlayerDAO;
import com.wtx.fifachamp.model.GolPartida;
import com.wtx.fifachamp.model.Jogador;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Player;
import com.wtx.fifachamp.model.Team;

import java.util.List;

public class GolsCadastroActivity extends AppCompatActivity {

    FloatingActionButton fabSalvarGols;
    Spinner spnJogadorGol;
    EditText edtQtdeGols;

    private GolPartida obj = null;
    private Player player = null;
    private Partida partida = null;
    private List<Jogador> jogadores = null;
    public static boolean INSERINDO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gols_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheDados();

        fabSalvarGols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salvar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if (spnJogadorGol.getSelectedItemPosition() == 0 || edtQtdeGols.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(),"Faltam dado a serem preenchidos", Toast.LENGTH_LONG).show();

                }else {
                    obj.setGols(Integer.parseInt(edtQtdeGols.getText().toString()));
                    obj.setJogador(jogadores.get(spnJogadorGol.getSelectedItemPosition() - 1));

                    //inserindo ou alterando o registro
                    if(INSERINDO)
                        new GolPartidaDAO(getApplicationContext()).insert(obj);
                    else
                        new GolPartidaDAO(getApplicationContext()).update(obj);

                    //atualizando a partida
                    if(partida.getPlayerOne().getId() == obj.getPlayer().getId())
                        partida.setGolsOne(partida.getGolsOne() + obj.getGols());
                    else
                        partida.setGolsTwo(partida.getGolsTwo() + obj.getGols());

                    new PartidaDAO(getApplicationContext()).update(partida);

                    PartidaCadastroActivity.ATUALIZAR = true;

                    Toast.makeText(getApplicationContext(),"Registro salvo com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
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
                new GolPartidaDAO(getApplicationContext()).delete(obj);

                //atualizando a partida
                if(obj.getPlayer().getId() == partida.getPlayerOne().getId())
                    partida.setGolsOne(partida.getGolsOne() - obj.getGols());
                else
                    partida.setGolsTwo(partida.getGolsTwo() - obj.getGols());

                new PartidaDAO(getApplicationContext()).update(partida);

                Toast.makeText(getApplicationContext(), "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        if(idItem == R.id.menuSair) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    private void preencheDados() {
        partida = (Partida) getIntent().getExtras().get("partida");

        if(INSERINDO) {
            player = (Player) getIntent().getExtras().get("player");
            jogadores = new JogadorDAO(getApplicationContext()).findAllByTeam(player.getTeam().getId());

            obj = new GolPartida();
            obj.setIdPartida(partida.getId());
            obj.setPlayer(player);

        }else {
            obj = (GolPartida) getIntent().getExtras().get("golpartida");
            jogadores = new JogadorDAO(getApplicationContext()).findAllByTeam(obj.getPlayer().getTeam().getId());
            edtQtdeGols.setText(String.valueOf(obj.getGols()));
            edtQtdeGols.setEnabled(false);
        }

        preencheSpn(jogadores);
    }

    private void preencheSpn(List<Jogador> jogadores) {
        String[] listaSpn;
        int position = 0;

        if(jogadores.size() > 0) {
            listaSpn = new String[jogadores.size() + 1];
            listaSpn[0] = "Selecione um Jogador ...";
            int idx = 1;

            for (Jogador j : jogadores) {

                if(!INSERINDO && j.getId() == obj.getJogador().getId())
                    position = idx;

                listaSpn[idx++] = j.getNome();
            }

        }else {
            listaSpn = new String[1];
            listaSpn[0] = "Não há Jogadores ...";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaSpn);

        spnJogadorGol.setAdapter(adapter);

        if (!INSERINDO && jogadores.size() > 0) {
            spnJogadorGol.setSelection(position);
            spnJogadorGol.setEnabled(false);
            fabSalvarGols.setEnabled(false);
            fabSalvarGols.setVisibility(View.INVISIBLE);
        }
    }

    private void binding() {
        fabSalvarGols = (FloatingActionButton) findViewById(R.id.fabSalvarGols);
        spnJogadorGol = findViewById(R.id.spnJogadorGol);
        edtQtdeGols = findViewById(R.id.edtQtdeGols);
    }

}
