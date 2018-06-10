package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.PlayerScore;

import java.util.List;

public class PlayerScoreAdapter extends BaseAdapter {
    private LayoutInflater layout;
    private final List<PlayerScore> list;

    public PlayerScoreAdapter(Context context, List<PlayerScore> list) {
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
        ImageView imgRanking;
        TextView tvRankPlayer, tvRankTeam, tvRankArtilheiro, tvGolsArtilheiro, tvPts;

        View view = layout.inflate(R.layout.item_ranking, parent,false);

        tvRankPlayer = view.findViewById(R.id.tvRankPlayer);
        tvRankTeam = view.findViewById(R.id.tvRankTeam);
        tvRankArtilheiro = view.findViewById(R.id.tvRankArtilheiro);
        tvGolsArtilheiro = view.findViewById(R.id.tvGolsArtilheiro);
        tvPts = view.findViewById(R.id.tvPts);
        imgRanking = view.findViewById(R.id.imgRanking);

        PlayerScore obj = (PlayerScore) getItem(position);

        tvRankPlayer.setText(obj.getPlayer().getNome());
        tvRankTeam.setText(obj.getPlayer().getTeam().getNome());
        tvRankArtilheiro.setText(obj.getArtilheiro().getNome());
        tvGolsArtilheiro.setText(obj.getGolsArtilheiro() + " Gols");
        tvPts.setText(String.valueOf(obj.getPts()) + " Pts");

        return view;
    }
}
