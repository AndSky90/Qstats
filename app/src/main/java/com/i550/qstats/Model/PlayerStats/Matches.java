package com.i550.qstats.Model.PlayerStats;

public class Matches {

    public String id;
    public String playedDateTime;
    public String gameMode;
    public String map;
    public String score;
    public String scoreLimit;
    public String timeLimit;

    public String getId() {
        return id;
    }

    public String getPlayedDateTime() {
        return playedDateTime;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getMap() {
        return map;
    }

    public String getScore() {
        return score;
    }

    public String getScoreLimit() {
        return scoreLimit;
    }

    public String getTimeLimit() {
        return timeLimit;
    }
    /* @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("playedDateTime", playedDateTime).append("gameMode", gameMode).append("map", map).append("score", score).append("scoreLimit", scoreLimit).append("timeLimit", timeLimit).toString();
    }*/
}