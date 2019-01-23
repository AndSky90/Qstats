package com.i550.qstats.Model.PlayerSummary;

import java.util.ArrayList;

public class MatchDetails {
    private String id;
    private String time;
    private String mapName;
    private String  rank;
    private ArrayList<String> score = null;
    private String gameMode;
    private Boolean won;
    private Integer xp;
    private Float kdr;
    private Integer totalDamage;
    private WeaponAccuracy weaponAccuracy;

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getMapName() {
        return mapName;
    }

    public String  getRank() {
        return rank;
    }

    public ArrayList<String> getScore() {
        return score;
    }

    public String getGameMode() {
        return gameMode;
    }

    public Boolean getWon() {
        return won;
    }

    public Integer getXp() {
        return xp;
    }

    public Float getKdr() {
        return kdr;
    }

    public Integer getTotalDamage() {
        return totalDamage;
    }

    public WeaponAccuracy getWeaponAccuracy() {
        return weaponAccuracy;
    }
}