package com.i550.qstats.Model.PlayerStats;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerRatings {
    private Duel duel = new Duel();
    private Tdm tdm = new Tdm();

    public int getDuelRating() {
        return duel.getRating();
    }

    class Duel {

        @SerializedName("rating")
        @Expose
        public Integer rating;
        @SerializedName("deviation")
        @Expose
        public Integer deviation;
        @SerializedName("volitility")
        @Expose
        public Float volitility;
        @SerializedName("lastUpdated")
        @Expose
        public String lastUpdated;
        @SerializedName("gamesCount")
        @Expose
        public Integer gamesCount;
        @SerializedName("lastChange")
        @Expose
        public Integer lastChange;
        @SerializedName("history")
        @Expose
        public List<History> history = new ArrayList<>();

        public Integer getRating() {
            return rating;
        }
        /*   @Override
    public String toString() {
        return new ToStringBuilder(this).append("rating", rating).append("deviation", deviation).append("volitility", volitility).append("lastUpdated", lastUpdated).append("gamesCount", gamesCount).append("lastChange", lastChange).append("history", history).toString();
    }*/
    }

    class Tdm {

        @SerializedName("rating")
        @Expose
        public Integer rating;
        @SerializedName("deviation")
        @Expose
        public Integer deviation;
        @SerializedName("volitility")
        @Expose
        public Float volitility;
        @SerializedName("lastUpdated")
        @Expose
        public String lastUpdated;
        @SerializedName("gamesCount")
        @Expose
        public Integer gamesCount;
        @SerializedName("lastChange")
        @Expose
        public Integer lastChange;
        @SerializedName("history")
        @Expose
        public List<History> history = new ArrayList<>();
     /*   @Override
        public String toString() {
            return new ToStringBuilder(this).append("rating", rating).append("deviation", deviation).append("volitility", volitility).append("lastUpdated", lastUpdated).append("gamesCount", gamesCount).append("lastChange", lastChange).append("history", history).toString();
        }*/
    }

    class History {

        @SerializedName("gamesPlayed")
        @Expose
        public Integer gamesPlayed;
        @SerializedName("eloRating")
        @Expose
        public Integer eloRating;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("result")
        @Expose
        public Integer result;
        @SerializedName("sessionId")
        @Expose
        public String sessionId;

   /* @Override
    public String toString() {
        return new ToStringBuilder(this).append("gamesPlayed", gamesPlayed).append("eloRating", eloRating).append("time", time).append("result", result).append("sessionId", sessionId).toString();
    }*/
    }

}





