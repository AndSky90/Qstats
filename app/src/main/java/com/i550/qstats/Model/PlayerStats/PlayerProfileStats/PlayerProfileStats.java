package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class PlayerProfileStats {

    @SerializedName("champions")
    private Map<String, Champions> champions = new LinkedHashMap<>();

    public void generateAll() {
        Champions all;                                            //создаем чемпиона ALL
        LinkedHashMap<String, GameModes> gameModesOfAllChamp = new LinkedHashMap<>();                      //со всеми его полями
        Map<String, DamageStatusList> damageStatusListOfAllChamp = new HashMap<>();
        if (champions.containsKey("ALL")) champions.remove("ALL");
        for (Champions c : champions.values()) {                                    //для всех чемпионов
            c.generateAllGameModes();
            for (String key : c.getGameModes().keySet()) {                          //для каждого члена GameModes чемпиона==============
                GameModes gm = c.getGameModes().get(key);                           //у GameModes чемпиона ALL берем тот же ключ
                GameModes value;
                if (gameModesOfAllChamp.containsKey(key)) value = gameModesOfAllChamp.get(key);
                else value = new GameModes();

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

                gameModesOfAllChamp.put(key, value);                                   //итог операции с 1 чемпионом вливаем в ALL
            }

            for (String key : c.getDamageStatusList().keySet()) {                 //для каждого члена DamageStatusList чемпиона
                DamageStatusList dsl = c.getDamageStatusList().get(key);
                DamageStatusList value;                                         //у DamageStatusList чемпиона ALL берем тот же ключ
                if (damageStatusListOfAllChamp.containsKey(key)) {
                    value = damageStatusListOfAllChamp.get(key);
                } else {
                    value = new DamageStatusList();
                }

                value.setDamage(value.getDamage() + dsl.getDamage());                   //суммируем каждое поле каждого члена DamageStatusList
                value.setHeadshots(value.getHeadshots() + dsl.getHeadshots());
                value.setHits(value.getHits() + dsl.getHits());
                value.setKills(value.getKills() + dsl.getKills());
                value.setShots(value.getShots() + dsl.getShots());

                damageStatusListOfAllChamp.put(key, value);                                     // возвращаем суммированное значение обратно в DamageStatusList чемпиона ALL
            }

        }

        String medalsOfAllChamp = "null";
        all = new Champions(gameModesOfAllChamp, damageStatusListOfAllChamp, medalsOfAllChamp);              // собираем чемпиона ALL воедино
        Map<String, Champions> allChamps = new LinkedHashMap<>();
        allChamps.put("ALL", all);
        allChamps.putAll(champions);
        champions = allChamps;
    }

    public Map<String, Champions> getChampions() {
        return champions;

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





