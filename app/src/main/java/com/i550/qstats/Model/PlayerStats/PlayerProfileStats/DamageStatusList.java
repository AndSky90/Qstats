package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

public class DamageStatusList {

    private int hits;
    private int shots;
    private int kills;
    private int headshots;
    private int damage;

    public int getHits() {
        return hits;
    }

    public int getShots() {
        return shots;
    }

    public int getKills() {
        return kills;
    }

    public int getHeadshots() {
        return headshots;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "DamageStatusList{" +
                "hits=" + hits +
                ", shots=" + shots +
                ", kills=" + kills +
                ", headshots=" + headshots +
                ", damage=" + damage +
                '}';
    }
}