package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.Player;

import java.util.List;

public class ItemGeracaoAdapter extends BaseAdapter {
    private LayoutInflater layout;
    private final List<Player> list;

    public ItemGeracaoAdapter(Context context, List<Player> list) {
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
        TextView tvNomePlayer;

        View view = layout.inflate(R.layout.item_geracao_player, parent,false);
        tvNomePlayer = view.findViewById(R.id.txt_player);

        Player obj = (Player) getItem(position);
        tvNomePlayer.setText(obj.getNome());

        return view;
    }
}
