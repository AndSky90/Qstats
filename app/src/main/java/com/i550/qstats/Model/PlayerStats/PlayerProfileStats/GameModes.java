package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class GameModes {
    public GameModes() {
    }

    @SerializedName("won")
    private int won = 0 ;
    @SerializedName("lost")
    private int lost = 0;
    @SerializedName("tie")
    private int tie = 0;
    @SerializedName("lifeTime")
    private int lifeTime = 0;
    @SerializedName("timePlayed")
    private int timePlayed = 0;
    @SerializedName("kills")
    private int kills = 0;
    @SerializedName("deaths")
    private int deaths = 0;
    @SerializedName("powerPickups")
    private int powerPickups = 0;
    @SerializedName("megaHealthPickups")
    private int megaHealthPickups = 0;
    @SerializedName("heavyArmorPickups")
    private int heavyArmorPickups = 0;
    @SerializedName("tacticalPickups")
    private int tacticalPickups = 0;
    @SerializedName("score")
    private int score = 0;
    @SerializedName("captured")
    private int captured = 0;
    @SerializedName("defended")
    private int defended = 0;
    @SerializedName("scoringEvents")
    private Map<String, Integer> scoringEvents = new HashMap<>();
    @SerializedName("healed")
    private int healed = 0;
    @SerializedName("smallArmorPickups")
    private int smallArmorPickups = 0;
    @SerializedName("rankedWon")
    private int rankedWon = 0;
    @SerializedName("rankedLost")
    private int rankedLost = 0;
    @SerializedName("rankedTied")
    private int rankedTied = 0;
    @SerializedName("rankedTimePlayed")
    private int rankedTimePlayed = 0;

    public int getWon() {
        return won;
    }
    public int getLost() {
        return lost;
    }
    public int getTie() {
        return tie;
    }
    public int getLifeTime() {
        return lifeTime;
    }
    public int getTimePlayed() {
        return timePlayed;
    }
    public int getKills() {
        return kills;
    }
    public int getDeaths() {
        return deaths;
    }
    public int getPowerPickups() {
        return powerPickups;
    }
    public int getMegaHealthPickups() {
        return megaHealthPickups;
    }
    public int getHeavyArmorPickups() {
        return heavyArmorPickups;
    }
    public int getTacticalPickups() {
        return tacticalPickups;
    }
    public int getScore() {
        return score;
    }
    public int getCaptured() {
        return captured;
    }
    public int getDefended() {
        return defended;
    }
    public Map<String, Integer> getScoringEvents() {
        return scoringEvents;
    }
    public int getHealed() {
        return healed;
    }
    public int getSmallArmorPickups() {
        return smallArmorPickups;
    }
    public int getRankedWon() {
        return rankedWon;
    }
    public int getRankedLost() {
        return rankedLost;
    }
    public int getRankedTied() {
        return rankedTied;
    }
    public int getRankedTimePlayed() {
        return rankedTimePlayed;
    }

    public void setWon(int won) {
        this.won = won;
    }
    public void setLost(int lost) {
        this.lost = lost;
    }
    public void setTie(int tie) {
        this.tie = tie;
    }
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
    public void setPowerPickups(int powerPickups) {
        this.powerPickups = powerPickups;
    }
    public void setMegaHealthPickups(int megaHealthPickups) {
        this.megaHealthPickups = megaHealthPickups;
    }
    public void setHeavyArmorPickups(int heavyArmorPickups) {
        this.heavyArmorPickups = heavyArmorPickups;
    }
    public void setTacticalPickups(int tacticalPickups) {
        this.tacticalPickups = tacticalPickups;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setCaptured(int captured) {
        this.captured = captured;
    }
    public void setDefended(int defended) {
        this.defended = defended;
    }
    public void setScoringEvents(Map<String, Integer> scoringEvents) {
        this.scoringEvents = scoringEvents;
    }
    public void setHealed(int healed) {
        this.healed = healed;
    }
    public void setSmallArmorPickups(int smallArmorPickups) {
        this.smallArmorPickups = smallArmorPickups;
    }
    public void setRankedWon(int rankedWon) {
        this.rankedWon = rankedWon;
    }
    public void setRankedLost(int rankedLost) {
        this.rankedLost = rankedLost;
    }
    public void setRankedTied(int rankedTied) {
        this.rankedTied = rankedTied;
    }
    public void setRankedTimePlayed(int rankedTimePlayed) {
        this.rankedTimePlayed = rankedTimePlayed;
    }
}
