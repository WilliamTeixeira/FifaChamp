package com.wtx.fifachamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtx.fifachamp.R;
import com.wtx.fifachamp.model.Team;

import java.util.List;

/**
 * Created by willi on 14/04/2018.
 */

public class TeamAdapter extends BaseAdapter {
    private LayoutInflater layout;
    private final List<Team> list;

    public TeamAdapter(Context context, List<Team> list) {
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
        TextView tvItemNomeTeam;

        View view = layout.inflate(R.layout.item_teams, parent,false);

        tvItemNomeTeam = view.findViewById(R.id.tvItemNomeTeam);

        Team obj = (Team) getItem(position);
        tvItemNomeTeam.setText(obj.getNome());

        return view;
    }
}
