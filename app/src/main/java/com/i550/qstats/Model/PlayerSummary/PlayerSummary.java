package com.i550.qstats.Model.PlayerSummary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlayerSummary {
    public PlayerSummary() {}
    @SerializedName("matches")
    private List<MatchDetails> match = new ArrayList<>();

    public List<MatchDetails> getMatch() {
        return match;
    }

    @Override
    public String toString() {
        return "PlayerSummary{" + "matches=" + match + '}';
    }
}

class WeaponAccuracy {
    public Float gAUNTLET;
    public Float mACHINEGUN;
    public Float mACHINEGUNGRADE1;
    public Float sHOTGUN;
    public Float sHOTGUNGRADE1;
    public Float nAILGUN;
    public Float nAILGUNGRADE1;
    public Float rOCKETLAUNCHER;
    public Float lIGHTNINGGUN;
    public Float rAILGUN;
    public Float lAGBOLT;
}



