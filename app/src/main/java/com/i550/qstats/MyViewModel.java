package com.i550.qstats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;

//__________________________________________________________________________________________________
public class MyViewModel extends ViewModel {

    private static final String TAG = "qStatserViewModel";
    //   private Gson gson = new GsonBuilder().registerTypeAdapter(PlayerStats.class, new PlayerStatsDeserializer()).create();
    private Gson gson = new Gson();
    private DataGlobal dataGlobal = new DataGlobal();
    private LeaderBoard tdmLeads = new LeaderBoard();
    private LeaderBoard duelLeads = new LeaderBoard();
    private PlayerStats playerStats = new PlayerStats();
    private PlayerSummary playerSummary = new PlayerSummary();
    static Boolean emptyDb = true;

    public DataGlobal getDataGlobal() {
        return dataGlobal;
    }

    private void setDataGlobal(DataGlobal dataGlobal) {
        this.dataGlobal = dataGlobal;
        Log.i(TAG, "setDataGlobal: " + dataGlobal.getTotalChampionusage() + "\n");
    }

     LeaderBoard getTdmLeads() {
        return tdmLeads;
    }

    private void setTDMLeads(LeaderBoard tdmLeads) {
        this.tdmLeads = tdmLeads;
        Log.i(TAG, "setTDMLeads : ");
    }

     LeaderBoard getDuelLeads() {
        return duelLeads;
    }

    private void setDuelLeads(LeaderBoard duelLeads) {
        this.duelLeads = duelLeads;
        Log.i(TAG, "setDuelLeads : ");
    }

     PlayerStats getPlayerStats() {
        return playerStats;
    }

    private void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
        Log.i(TAG, "setPlayerStats : ");
    }

     PlayerSummary getPlayerSummary() {
        return playerSummary;
    }

    private void setPlayerSummary(PlayerSummary playerSummary) {
        this.playerSummary = playerSummary;
        Log.i(TAG, "setPlayerSummary : ");
    }


    void fetchDataGlobal(String jsonString) {
        if (jsonString != null) {
            DataGlobal data = gson.fromJson(jsonString, DataGlobal.class);
            Log.i(TAG, "String Object global: " + data.getTotalChampionusage() + "\n");
            setDataGlobal(data);
        }
    }

    void fetchLeaderBoard(String jsonString, Boolean mode) {
        if (jsonString != null) {
            LeaderBoard data = gson.fromJson(jsonString, LeaderBoard.class);
            Log.i(TAG, "String Object tdm: " + data.toString());
            if (mode) setDuelLeads(data);
            else setTDMLeads(data);
        }
    }

    void fetchPlayerSummary(String jsonString) {
        if (jsonString != null) {
            PlayerSummary data = gson.fromJson(jsonString, PlayerSummary.class);
            Log.i(TAG, "String Object summary: " + data.toString());
            setPlayerSummary(data);
        }
    }

    void fetchPlayerStats(String jsonString) {
        if (jsonString != null) {
            PlayerStats data = gson.fromJson(jsonString, PlayerStats.class);
            Log.i(TAG, "String Object stats: " + data.toString());
            data.getPlayerProfileStats().generateAll();     //генерируем чемпиона ALL
            setPlayerStats(data);
        }
    }

     String[] fetchSearchResult(String jsonString) {
        String[] result = null;
        if (jsonString != null && jsonString.length() > 0) {
            JsonParser jsonParser = new JsonParser();
            JsonArray a = (JsonArray) jsonParser.parse(jsonString);
            result = new String[a.size()];
            for (int i = 0; i < a.size(); i++) { //&& i<=20
                JsonObject o = (JsonObject) a.get(i);
                JsonPrimitive p = (JsonPrimitive) o.get("entityName");
                String s = p.getAsString();
                result[i] = s;
            } // Log.i(TAG, "result: " + result[0] + " , " + result[1]);
        }
        return result;
    }
}
