package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

public class DamageStatusList {

    private int hits = 0;
    private int shots = 0;
    private int kills = 0;
    private int headshots = 0;
    private int damage = 0;

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

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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