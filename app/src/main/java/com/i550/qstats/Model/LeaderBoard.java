package com.i550.qstats.Model;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    public LeaderBoard() { }

    private List<Entry> entries = new ArrayList<>();
    private int totalEntries;

    @Override
    public String toString() {
        return "LeaderBoard{" +
                "entries=" + entries +
                ", totalEntries=" + totalEntries +
                '}';
    }
}

class Entry {
        public String userName;
        public Integer eloRating;
        public String profileIconId;
        public String namePlateId;
    }
