package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.Jogador;

import java.util.List;

/**
 * Created by willi on 15/04/2018.
 */

public class JogadorAdapter extends BaseAdapter{
    private LayoutInflater layout;
    private final List<Jogador> list;

    public JogadorAdapter (Context context, List<Jogador> list) {
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
        TextView tvItemNomeJogador;

        View view = layout.inflate(R.layout.item_jogador, parent,false);

        tvItemNomeJogador = view.findViewById(R.id.tvItemNomeJogador);

        Jogador obj = (Jogador) getItem(position);
        tvItemNomeJogador.setText(obj.getNome());

        return view;
    }
}
