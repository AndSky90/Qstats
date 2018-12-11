package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerProfileStats {

    @SerializedName("champions")
    private Map<String, Champions> champions = new HashMap<>();

    public Map<String, Champions> getChampions() {
        return champions;
    }
    public ArrayList<Champions> getChampionsValuesArray() {
        return (ArrayList<Champions>) champions.values();}

        public ArrayList<String> getChampionsNamesArray() {
            return new ArrayList<>(champions.keySet());
    }
}





