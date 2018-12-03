package com.i550.qstats.Model;

import java.util.List;

public class PlayerSummary {
    public PlayerSummary() {}

    @Override
    public String toString() {
        return "PlayerSummary{" +
                "matches=" + matches +
                '}';
    }

    public List<Match> matches = null;
}


class Match {
    public String id;
    public String time;
    public String mapName;
    public Integer rank;
    public List<Integer> score = null;
    public String gameMode;
    public Boolean won;
    public Integer xp;
    public Float kdr;
    public Integer totalDamage;
    public WeaponAccuracy weaponAccuracy;
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
