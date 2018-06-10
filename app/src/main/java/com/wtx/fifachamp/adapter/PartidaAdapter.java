package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.Partida;
import com.wtx.fifachamp.model.Player;

import java.util.List;

public class PartidaAdapter extends BaseAdapter {
    private LayoutInflater layout;
    private final List<Partida> list;

    public PartidaAdapter(Context context, List<Partida> list) {
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
        TextView tvNomePlayerOne, tvNomeTimePlayerOne, tvGolsOne, tvPtsOne, tvNomePlayerTwo, tvNomeTimePlayerTwo, tvGolsTwo, tvPtsTwo;

        View view = layout.inflate(R.layout.item_partidas, parent,false);

        tvNomePlayerOne = view.findViewById(R.id.tvNomePlayerOne);
        tvNomeTimePlayerOne = view.findViewById(R.id.tvNomeTimePlayerOne);
        tvGolsOne = view.findViewById(R.id.tvGolsOne);
        tvPtsOne = view.findViewById(R.id.tvPtsOne);

        tvNomePlayerTwo = view.findViewById(R.id.tvNomePlayerTwo);
        tvNomeTimePlayerTwo = view.findViewById(R.id.tvNomeTimePlayerTwo);
        tvGolsTwo = view.findViewById(R.id.tvGolsTwo);
        tvPtsTwo = view.findViewById(R.id.tvPtsTwo);

        //Preenche o activity
        Partida obj = (Partida) getItem(position);

        tvNomePlayerOne.setText(obj.getPlayerOne().getNome());
        tvNomeTimePlayerOne.setText(obj.getPlayerOne().getTeam().getNome());
        tvGolsOne.setText(String.valueOf(obj.getGolsOne()));
        tvPtsOne.setText(String.valueOf(obj.getPtsOne()) + " Pts");

        tvNomePlayerTwo.setText(obj.getPlayerTwo().getNome());
        tvNomeTimePlayerTwo.setText(obj.getPlayerTwo().getTeam().getNome());
        tvGolsTwo.setText(String.valueOf(obj.getGolsTwo()));
        tvPtsTwo.setText(String.valueOf(obj.getPtsTwo()) + " Pts");

        return view;
    }
}
