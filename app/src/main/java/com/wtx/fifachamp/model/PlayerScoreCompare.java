package com.wtx.fifachamp.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class PlayerScoreCompare implements Comparator<PlayerScore>{

    @Override
    public int compare(PlayerScore o1, PlayerScore o2) {
        if (o1.getPts() > o2.getPts())
            return 1;
        else if (o1.getPts() < o2.getPts())
            return -1;
        else
            return 0;
    }


}
