package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Rodada;

import java.util.List;

public class RodadaAdapter extends BaseAdapter{
    private LayoutInflater layout;
    private final List<Rodada> list;

    public RodadaAdapter(Context context, List<Rodada> list) {
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
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvIdRodada, tvNumeroPartidas, tvNumeroGols;

        View view = layout.inflate(R.layout.item_rodada, parent,false);

        tvIdRodada = view.findViewById(R.id.tvIdRodada);
        tvNumeroPartidas = view.findViewById(R.id.tvNumeroPartidas);
        tvNumeroGols = view.findViewById(R.id.tvNumeroGols);

        //Preenche o activity
        Rodada obj = (Rodada) getItem(position);

        tvIdRodada.setText(String.valueOf(obj.getId()) + "º Rodada");
        tvNumeroPartidas.setText(String.valueOf(obj.getTotalPartidas()) + " : Partidas");
        tvNumeroGols.setText(String.valueOf(obj.getTotalGols()) + " : Gols");

        return view;
    }
}
