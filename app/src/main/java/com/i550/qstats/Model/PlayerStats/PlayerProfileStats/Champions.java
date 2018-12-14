package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Champions {
    @SerializedName("gameModes")
    private Map<String, GameModes> gameModes = new LinkedHashMap<>();
    @SerializedName("damageStatusList")
    private Map<String, DamageStatusList> damageStatusList = new LinkedHashMap<>();
    @SerializedName("medals")
    private String medals;

    public Champions(){}
    public Champions(Map<String, GameModes> gameModes, Map<String, DamageStatusList> damageStatusList, String medals) {
        this.gameModes = gameModes;
        this.damageStatusList = damageStatusList;
        this.medals = medals;
    }

    public Map<String, GameModes> getGameModes() {
        return gameModes;
    }

    public ArrayList<String> getGameModesTitles() {
        return new ArrayList<>(gameModes.keySet());
    }

    public ArrayList<GameModes> getGameModesValues() {
        ArrayList<GameModes> modes = new ArrayList<>();
        for (GameModes p : gameModes.values()) modes.add(p);
        return modes;
    }
    public Map<String, DamageStatusList> getDamageStatusList() {
        return damageStatusList;
    }
    public List<DamageStatusList> getWeaponsStats() {
        List<DamageStatusList> weapons = new ArrayList<>();
        weapons.add(damageStatusList.get("GAUNTLET"));
        weapons.add(damageStatusList.get("MACHINEGUN"));
        weapons.add(damageStatusList.get("MACHINEGUN_GRADE1"));
        weapons.add(damageStatusList.get("SHOTGUN"));
        weapons.add(damageStatusList.get("SHOTGUN_GRADE1"));
        weapons.add(damageStatusList.get("NAILGUN"));
        weapons.add(damageStatusList.get("NAILGUN_GRADE1"));
        weapons.add(damageStatusList.get("LAGBOLT"));
        weapons.add(damageStatusList.get("ROCKET_LAUNCHER"));
        weapons.add(damageStatusList.get("LIGHTNING_GUN"));
        weapons.add(damageStatusList.get("RAILGUN"));
        Log.i("qStats", " GetWeaponStats: " + damageStatusList.get("GAUNTLET").toString() + " " + damageStatusList.get("ROCKET_LAUNCHER").toString() );
        return weapons;
    }
    public String getMedals() {
        return medals;
    }

    public void setGameModes(Map<String, GameModes> gameModes) {
        this.gameModes = gameModes;
    }

    public void setDamageStatusList(Map<String, DamageStatusList> damageStatusList) {
        this.damageStatusList = damageStatusList;
    }

    public void setMedals(String medals) {
        this.medals = medals;
    }
}
