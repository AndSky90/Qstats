package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerProfileStats {

    @SerializedName("champions")
    private Map<String, Champions> champions = new LinkedHashMap<>();

    public Map<String, Champions> getChampions() {

        Map<String, Champions> allChamps = new LinkedHashMap<>();
        Champions all;                                            //создаем чемпиона ALL
        Map<String, GameModes> allGameModes = new HashMap<>();                      //со всеми его полями
        Map<String, DamageStatusList> allDamageStatusList = new HashMap<>();

        for (Champions c : champions.values()) {                                    //для всех чемпионов

            for (String key : c.getGameModes().keySet()) {                          //для всех gamemodes
                GameModes gm = c.getGameModes().get(key);                           //прибавляем данные гейммода в чемпиона ALL
                GameModes value;
                if (allGameModes.containsKey(key)) {
                    value = allGameModes.get(key);
                } else {value = new GameModes();}

                value.setWon(value.getWon() + gm.getWon());
                value.setLost(value.getLost() + gm.getLost());
                value.setTimePlayed(value.getTimePlayed() + gm.getTimePlayed());
                value.setKills(value.getKills() + gm.getKills());
                value.setDeaths(value.getDeaths() + gm.getDeaths());
                value.setPowerPickups(value.getPowerPickups() + gm.getPowerPickups());
                value.setTacticalPickups(value.getTacticalPickups() + gm.getTacticalPickups());

                allGameModes.put(key, value);                                   //итог операции с 1 гейммодом вливаем в ALL
            }

            for (String key: c.getDamageStatusList().keySet()){
                DamageStatusList dsl = c.getDamageStatusList().get(key);
                DamageStatusList value;
                if (allDamageStatusList.containsKey(key)) {
                    value = allDamageStatusList.get(key);
                } else {value = new DamageStatusList();}

                value.setDamage(value.getDamage() + dsl.getDamage());
                value.setHeadshots(value.getHeadshots() + dsl.getHeadshots());
                value.setHits(value.getHits() + dsl.getHits());
                value.setKills(value.getKills() + dsl.getKills());
                value.setShots(value.getShots() + dsl.getShots());

                allDamageStatusList.put(key,value);
            }
        }

        String allMedals = "null";
        all = new Champions(allGameModes, allDamageStatusList, allMedals);
        allChamps.put("ALL", all);
        allChamps.putAll(champions);

        return allChamps;

    }

    public ArrayList<Champions> getChampionsValuesArray() {
        ArrayList<Champions> c = new ArrayList<>();
        for (Champions p : getChampions().values()) c.add(p);
        return c;
    }

    public ArrayList<String> getChampionsNamesArray() {
        ArrayList<String> names = new ArrayList<>(getChampions().keySet());
        //  names.add(0, "ALL");                    // add "ALL" champion for common info
        return names;
    }

}





