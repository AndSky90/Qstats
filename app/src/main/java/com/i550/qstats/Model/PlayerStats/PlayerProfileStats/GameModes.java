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

   /* @Override
    public String toString() {
        return new ToStringBuilder(this).append("won", won).append("lost", lost).append("tie", tie).append("lifeTime", lifeTime).append("timePlayed", timePlayed).append("kills", kills).append("deaths", deaths).append("powerPickups", powerPickups).append("megaHealthPickups", megaHealthPickups).append("heavyArmorPickups", heavyArmorPickups).append("tacticalPickups", tacticalPickups).append("score", score).append("captured", captured).append("defended", defended).append("scoringEvents", scoringEvents).append("healed", healed).append("smallArmorPickups", smallArmorPickups).append("rankedWon", rankedWon).append("rankedLost", rankedLost).append("rankedTied", rankedTied).append("rankedTimePlayed", rankedTimePlayed).toString();
    }*/

}
