package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Champions {
    @SerializedName("gameModes")
    private Map<String, GameModes> gameModes = new HashMap<>();
    @SerializedName("damageStatusList")
    private Map<String, DamageStatusList> damageStatusList = new HashMap<>();
    @SerializedName("medals")
    private String medals;

    public Map<String, GameModes> getGameModes() {
        return gameModes;
    }

    public Map<String, DamageStatusList> getDamageStatusList() {
        return damageStatusList;
    }

    public String getMedals() {
        return medals;
    }
}
