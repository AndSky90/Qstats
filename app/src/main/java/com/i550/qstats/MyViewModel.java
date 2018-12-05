package com.i550.qstats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats;
import com.i550.qstats.Model.PlayerSummary;

//__________________________________________________________________________________________________

public class MyViewModel extends AndroidViewModel {
    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    private static final String TAG = "qStatserViewModel";
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    private static DataGlobal dataGlobal = new DataGlobal();
    private static LeaderBoard tdmLeads = new LeaderBoard();
    private static LeaderBoard duelLeads = new LeaderBoard();
    private static PlayerStats playerStats = new PlayerStats();
    private static PlayerSummary playerSummary = new PlayerSummary();

    public static DataGlobal getDataGlobal() {
        return dataGlobal;
    }
    private static void setDataGlobal(DataGlobal dataGlobal) {
        MyViewModel.dataGlobal = dataGlobal;
        Log.i(TAG, "setDataGlobal: " + MyViewModel.dataGlobal.getTotal_championusage() +"\n" );
    }

    public static LeaderBoard getTdmLeads() {
        return tdmLeads;
    }
    private static void setTDMLeads(LeaderBoard tdmLeads) {
        MyViewModel.tdmLeads = tdmLeads;
        Log.i(TAG, "setTDMLeads : "  );
    }

    public static LeaderBoard getDuelLeads() {
        return duelLeads;
    }
    private static void setDuelLeads(LeaderBoard duelLeads) {
        MyViewModel.duelLeads = duelLeads;
        Log.i(TAG, "setDuelLeads : "  );
    }

    public static PlayerStats getPlayerStats() {
        return playerStats;
    }
    private static void setPlayerStats(PlayerStats playerStats) {
        MyViewModel.playerStats = playerStats;
        Log.i(TAG, "setPlayerStats : " );
    }

    public static PlayerSummary getPlayerSummary() {
        return playerSummary;
    }
    private static void setPlayerSummary(PlayerSummary playerSummary) {
        MyViewModel.playerSummary = playerSummary;
        Log.i(TAG, "setPlayerSummary : " );
    }


    void fetchDataGlobal(String jsonString) {       //получает JSON и отдает в модель
        DataGlobal data = gson.fromJson(jsonString, DataGlobal.class);
        Log.i(TAG, "String Object global: " + data.getTotal_championusage() + "\n");
        setDataGlobal(data);
    }

    void fetchLeaderBoard(String jsonString, Boolean mode) {
        LeaderBoard data = gson.fromJson(jsonString, LeaderBoard.class);
        Log.i(TAG, "String Object tdm: " + data.toString());
        if (mode) MyViewModel.setDuelLeads(data);
        setTDMLeads(data);
    }

    void fetchPlayerSummary(String jsonString) {
        PlayerSummary data = gson.fromJson(jsonString, PlayerSummary.class);
        Log.i(TAG, "String Object summary: " + data.toString());
        setPlayerSummary(data);
    }

    void fetchPlayerStats(String jsonString) {
        PlayerStats data = gson.fromJson(jsonString, PlayerStats.class);
        Log.i(TAG, "String Object stats: " + data.toString());
        setPlayerStats(data);
    }

}
