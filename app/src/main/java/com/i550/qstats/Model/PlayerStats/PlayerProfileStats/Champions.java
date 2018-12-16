package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Champions {
    @SerializedName("gameModes")
    private LinkedHashMap<String, GameModes> gameModes = new LinkedHashMap<>();
    @SerializedName("damageStatusList")
    private Map<String, DamageStatusList> damageStatusList = new LinkedHashMap<>();
    @SerializedName("medals")
    private String medals;

    public Champions(){}

    public Champions(LinkedHashMap<String, GameModes> gameModes, Map<String, DamageStatusList> damageStatusList, String medals) {
        this.gameModes = gameModes;
        this.damageStatusList = damageStatusList;
        this.medals = medals;
    }
    public LinkedHashMap<String, GameModes> getGameModes() {
        return gameModes;
    }
    public void generateAllGameModes() {
        LinkedHashMap<String, GameModes> allGameModes = new LinkedHashMap<>();
        GameModes value = new GameModes();                    //создаем GameModesAll


        for (String gmTitle : gameModes.keySet()) {                          //для каждого члена GameModes чемпиона //для каждого гейммод берем члены и засовываем в алл
            GameModes gm = gameModes.get(gmTitle);                           //у GameModes чемпиона ALL берем тот же ключ

            value.setWon(value.getWon() + gm.getWon());                  //суммируем каждое поле каждого члена GameModes
            value.setLost(value.getLost() + gm.getLost());
            value.setTimePlayed(value.getTimePlayed() + gm.getTimePlayed());
            value.setKills(value.getKills() + gm.getKills());
            value.setDeaths(value.getDeaths() + gm.getDeaths());
            value.setPowerPickups(value.getPowerPickups() + gm.getPowerPickups());
            value.setTacticalPickups(value.getTacticalPickups() + gm.getTacticalPickups());

            Map<String, Integer> allScoringEvent = value.getScoringEvents();
            Map<String, Integer> currScoringEvent = gm.getScoringEvents();
            for (String keySe : currScoringEvent.keySet()) {
                if (!allScoringEvent.containsKey(keySe)) allScoringEvent.put(keySe, 0);
                allScoringEvent.put(keySe, allScoringEvent.get(keySe) + currScoringEvent.get(keySe));
            }
            value.setScoringEvents(allScoringEvent);
        }

       allGameModes.put("GameModeAll", value);
        allGameModes.putAll(gameModes);
        gameModes = allGameModes;
    }

    public ArrayList<String> getGameModesTitles() {
        return new ArrayList<>(getGameModes().keySet());
    }

    public ArrayList<GameModes> getGameModesValues() {
        ArrayList<GameModes> modes = new ArrayList<>();
        for (GameModes p : getGameModes().values()) modes.add(p);
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

    public void setGameModes(LinkedHashMap<String, GameModes> gameModes) {
        this.gameModes = gameModes;
    }

    public void setDamageStatusList(Map<String, DamageStatusList> damageStatusList) {
        this.damageStatusList = damageStatusList;
    }

    public void setMedals(String medals) {
        this.medals = medals;
    }
}
