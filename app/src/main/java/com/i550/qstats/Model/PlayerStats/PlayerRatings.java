package com.i550.qstats.Model.PlayerStats;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerRatings {
    @SerializedName("duel")
    private Duel duel = new Duel();
    @SerializedName("tdm")
    private Duel tdm = new Duel();

    public int getDuelRating() {
        return duel.getRating();
    }

    class Duel {

        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("deviation")
        @Expose
        private Integer deviation;
        @SerializedName("volitility")
        @Expose
        private Float volitility;
        @SerializedName("lastUpdated")
        @Expose
        private String lastUpdated;
        @SerializedName("gamesCount")
        @Expose
        private Integer gamesCount;
        @SerializedName("lastChange")
        @Expose
        private Integer lastChange;
        @SerializedName("history")
        @Expose
        private List<History> history = new ArrayList<>();

        public Integer getDeviation() {
            return deviation;
        }

        public Float getVolitility() {
            return volitility;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public Integer getGamesCount() {
            return gamesCount;
        }

        public Integer getLastChange() {
            return lastChange;
        }

        public List<History> getHistory() {
            return history;
        }

        public Integer getRating() {
            return rating;
        }
        /*   @Override
    public String toString() {
        return new ToStringBuilder(this).append("rating", rating).append("deviation", deviation).append("volitility", volitility).append("lastUpdated", lastUpdated).append("gamesCount", gamesCount).append("lastChange", lastChange).append("history", history).toString();
    }*/
    }


    class History {

        @SerializedName("gamesPlayed")
        @Expose
        private Integer gamesPlayed;
        @SerializedName("eloRating")
        @Expose
        private Integer eloRating;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("result")
        @Expose
        private Integer result;
        @SerializedName("sessionId")
        @Expose
        private String sessionId;

        public Integer getGamesPlayed() {
            return gamesPlayed;
        }

        public Integer getEloRating() {
            return eloRating;
        }

        public String getTime() {
            return time;
        }

        public Integer getResult() {
            return result;
        }

        public String getSessionId() {
            return sessionId;
        }
/* @Override
    public String toString() {
        return new ToStringBuilder(this).append("gamesPlayed", gamesPlayed).append("eloRating", eloRating).append("time", time).append("result", result).append("sessionId", sessionId).toString();
    }*/
    }

}





