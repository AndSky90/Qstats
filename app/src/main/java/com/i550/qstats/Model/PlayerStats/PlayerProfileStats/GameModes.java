package com.i550.qstats.Model.PlayerStats.PlayerProfileStats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameModes {

    @SerializedName("won")
    @Expose
    public Integer won;
    @SerializedName("lost")
    @Expose
    public Integer lost;
    @SerializedName("tie")
    @Expose
    public Integer tie;
    @SerializedName("lifeTime")
    @Expose
    public Integer lifeTime;
    @SerializedName("timePlayed")
    @Expose
    public Integer timePlayed;
    @SerializedName("kills")
    @Expose
    public Integer kills;
    @SerializedName("deaths")
    @Expose
    public Integer deaths;
    @SerializedName("powerPickups")
    @Expose
    public Integer powerPickups;
    @SerializedName("megaHealthPickups")
    @Expose
    public Integer megaHealthPickups;
    @SerializedName("heavyArmorPickups")
    @Expose
    public Integer heavyArmorPickups;
    @SerializedName("tacticalPickups")
    @Expose
    public Integer tacticalPickups;
    @SerializedName("score")
    @Expose
    public Integer score;
    @SerializedName("captured")
    @Expose
    public Integer captured;
    @SerializedName("defended")
    @Expose
    public Integer defended;
    @SerializedName("scoringEvents")
    @Expose
    public ScoringEvents scoringEvents = new ScoringEvents();
    @SerializedName("healed")
    @Expose
    public Integer healed;
    @SerializedName("smallArmorPickups")
    @Expose
    public Integer smallArmorPickups;
    @SerializedName("rankedWon")
    @Expose
    public Integer rankedWon;
    @SerializedName("rankedLost")
    @Expose
    public Integer rankedLost;
    @SerializedName("rankedTied")
    @Expose
    public Integer rankedTied;
    @SerializedName("rankedTimePlayed")
    @Expose
    public Integer rankedTimePlayed;

    public Integer getWon() {
        return won;
    }

    public Integer getLost() {
        return lost;
    }

    public Integer getTie() {
        return tie;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public Integer getTimePlayed() {
        return timePlayed;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getPowerPickups() {
        return powerPickups;
    }

    public Integer getMegaHealthPickups() {
        return megaHealthPickups;
    }

    public Integer getHeavyArmorPickups() {
        return heavyArmorPickups;
    }

    public Integer getTacticalPickups() {
        return tacticalPickups;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getCaptured() {
        return captured;
    }

    public Integer getDefended() {
        return defended;
    }

    public ScoringEvents getScoringEvents() {
        return scoringEvents;
    }

    public Integer getHealed() {
        return healed;
    }

    public Integer getSmallArmorPickups() {
        return smallArmorPickups;
    }

    public Integer getRankedWon() {
        return rankedWon;
    }

    public Integer getRankedLost() {
        return rankedLost;
    }

    public Integer getRankedTied() {
        return rankedTied;
    }

    public Integer getRankedTimePlayed() {
        return rankedTimePlayed;
    }
}
