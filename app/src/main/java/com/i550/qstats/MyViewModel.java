package com.i550.qstats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats.PlayerLoadOut;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;

import java.util.HashMap;
import java.util.Map;

//__________________________________________________________________________________________________

public class MyViewModel extends AndroidViewModel {
    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    private static final String TAG = "qStatserViewModel";
    //   private Gson gson = new GsonBuilder().registerTypeAdapter(PlayerStats.class, new PlayerStatsDeserializer()).create();
    private Gson gson = new Gson();
    private static DataGlobal dataGlobal = new DataGlobal();
    private static LeaderBoard tdmLeads = new LeaderBoard();
    private static LeaderBoard duelLeads = new LeaderBoard();
    private static PlayerStats playerStats = new PlayerStats();
    private static PlayerSummary playerSummary = new PlayerSummary();

    public static DataGlobal getDataGlobal() {
        return dataGlobal;
    }

    private void setDataGlobal(DataGlobal dataGlobal) {
        MyViewModel.dataGlobal = dataGlobal;
        Log.i(TAG, "setDataGlobal: " + MyViewModel.dataGlobal.getTotal_championusage() + "\n");

        Map<String, PlayerLoadOut> map = new HashMap<>();
        map.put("RANGER", new PlayerLoadOut("id12", "icon12"));
        map.put("GALENA", new PlayerLoadOut("id17", "icon17"));
        String json = gson.toJson(map);
        Log.i(TAG, "map : " + json);
    }

    public static LeaderBoard getTdmLeads() {
        return tdmLeads;
    }

    private static void setTDMLeads(LeaderBoard tdmLeads) {
        MyViewModel.tdmLeads = tdmLeads;
        Log.i(TAG, "setTDMLeads : ");
    }

    public static LeaderBoard getDuelLeads() {
        return duelLeads;
    }

    private static void setDuelLeads(LeaderBoard duelLeads) {
        MyViewModel.duelLeads = duelLeads;
        Log.i(TAG, "setDuelLeads : ");
    }

    public static PlayerStats getPlayerStats() {
        return playerStats;
    }

    private static void setPlayerStats(PlayerStats playerStats) {
        MyViewModel.playerStats = playerStats;
        Log.i(TAG, "setPlayerStats : ");
    }

    public static PlayerSummary getPlayerSummary() {
        return playerSummary;
    }

    private static void setPlayerSummary(PlayerSummary playerSummary) {
        MyViewModel.playerSummary = playerSummary;
        Log.i(TAG, "setPlayerSummary : ");
    }


    void fetchDataGlobal(String jsonString) {
        if (jsonString != null) {
            DataGlobal data = gson.fromJson(jsonString, DataGlobal.class);
            Log.i(TAG, "String Object global: " + data.getTotal_championusage() + "\n");
            setDataGlobal(data);
        }
    }

    void fetchLeaderBoard(String jsonString, Boolean mode) {
        if (jsonString != null) {
            LeaderBoard data = gson.fromJson(jsonString, LeaderBoard.class);
            Log.i(TAG, "String Object tdm: " + data.toString());
            if (mode) MyViewModel.setDuelLeads(data);
            setTDMLeads(data);
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
            } Log.i(TAG, "result: " + result[0] + " , " + result[1]);
        }
        return result;
    }
}
