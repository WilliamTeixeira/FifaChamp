package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.GolPartida;
import com.wtx.fifachamp.model.Jogador;

import java.util.List;

public class GolsPartidaAdapter extends BaseAdapter {
    private LayoutInflater layout;
    private final List<GolPartida> list;

    public GolsPartidaAdapter(Context context, List<GolPartida> list) {
        this.list = list;
        layout = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvJogadorGol, tvPlayerGol, tvGol;

        View view = layout.inflate(R.layout.item_gols_partida, parent,false);

        tvJogadorGol = view.findViewById(R.id.tvJogadorGol);
        tvPlayerGol = view.findViewById(R.id.tvPlayerGol);
        tvGol = view.findViewById(R.id.tvGol);

        GolPartida obj = (GolPartida) getItem(position);

        tvJogadorGol.setText(obj.getJogador().getNome());
        tvPlayerGol.setText(obj.getPlayer().getNome());
        tvGol.setText(String.valueOf(obj.getGols()));

        return view;
    }
}
