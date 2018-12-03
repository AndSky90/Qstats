package com.i550.qstats.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlayerStats {
    public PlayerStats() {}

    private String name;
    PlayerRatings playerRatings = new PlayerRatings();
    PlayerLoadOut playerLoadOut = new PlayerLoadOut();
    PlayerProfileStats playerProfileStats = new PlayerProfileStats();
    PlayerLevelState playerLevelState = new PlayerLevelState();
    List<Matches> matches = null;
}

class PlayerLevelState {
    public Integer level;
    public Integer exp;

   /* @Override
    public String toString() {
        return new ToStringBuilder(this).append("level", level).append("exp", exp).toString();
    }*/

}