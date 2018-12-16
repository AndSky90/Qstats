package com.i550.qstats.Model.PlayerStats;

import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.PlayerProfileStats;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats {
    public PlayerStats() {}

    private String name;
    private PlayerRatings playerRatings = new PlayerRatings();
    private PlayerLoadOut playerLoadOut = new PlayerLoadOut();
    private PlayerProfileStats playerProfileStats = new PlayerProfileStats();
    private PlayerLevelState playerLevelState = new PlayerLevelState();
    private List<Matches> matches = new ArrayList<>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PlayerRatings getPlayerRatings() {
        return playerRatings;
    }
    public void setPlayerRatings(PlayerRatings playerRatings) {
        this.playerRatings = playerRatings;
    }
    public PlayerLoadOut getPlayerLoadOut() {
        return playerLoadOut;
    }
    public void setPlayerLoadOut(PlayerLoadOut playerLoadOut) {
        this.playerLoadOut = playerLoadOut;
    }
    public PlayerProfileStats getPlayerProfileStats() {
        return playerProfileStats;
    }
    public void setPlayerProfileStats(PlayerProfileStats playerProfileStats) {
        this.playerProfileStats = playerProfileStats;
    }
    public PlayerLevelState getPlayerLevelState() {
        return playerLevelState;
    }
    public void setPlayerLevelState(PlayerLevelState playerLevelState) {
        this.playerLevelState = playerLevelState;
    }
    public List<Matches> getMatches() {
        return matches;
    }
    public void setMatches(List<Matches> matches) {
        this.matches = matches;
    }


}

