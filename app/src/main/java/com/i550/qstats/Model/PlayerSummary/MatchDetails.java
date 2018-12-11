package com.i550.qstats.Model.PlayerSummary;

import java.util.ArrayList;
import java.util.List;

public class MatchDetails {
    public String id;
    public String time;
    public String mapName;
    public String  rank;
    public List<String> score = null;
    public String gameMode;
    public Boolean won;
    public Integer xp;
    public Float kdr;
    public Integer totalDamage;
    public WeaponAccuracy weaponAccuracy;

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

    public List<String> getScore() {
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