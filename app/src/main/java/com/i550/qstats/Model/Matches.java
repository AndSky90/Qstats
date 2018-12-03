package com.i550.qstats.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;



public class Matches {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("playedDateTime")
    @Expose
    public String playedDateTime;
    @SerializedName("gameMode")
    @Expose
    public String gameMode;
    @SerializedName("map")
    @Expose
    public String map;
    @SerializedName("score")
    @Expose
    public String score;
    @SerializedName("scoreLimit")
    @Expose
    public Integer scoreLimit;
    @SerializedName("timeLimit")
    @Expose
    public Integer timeLimit;

   /* @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("playedDateTime", playedDateTime).append("gameMode", gameMode).append("map", map).append("score", score).append("scoreLimit", scoreLimit).append("timeLimit", timeLimit).toString();
    }*/

}

