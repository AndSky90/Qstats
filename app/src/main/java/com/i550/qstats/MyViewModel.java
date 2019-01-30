package com.i550.qstats;


import android.arch.lifecycle.ViewModel;
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

    private Gson gson = new Gson();
    private DataGlobal dataGlobal = new DataGlobal();           //пока не юзается нигде
    private LeaderBoard tdmLeads = new LeaderBoard();
    private LeaderBoard duelLeads = new LeaderBoard();
    private PlayerStats playerStats = new PlayerStats();
    private PlayerSummary playerSummary = new PlayerSummary();
    public Boolean emptyDb = true;

    @Override
    protected void onCleared() {
        emptyDb=true;
        super.onCleared();
    }

    private void setDataGlobal(DataGlobal dataGlobal) {
        this.dataGlobal = dataGlobal;
        Log.i(TAG, "setDataGlobal: " + dataGlobal.getTotalChampionusage() + "\n");
    }

    public LeaderBoard getTdmLeads() {
        return tdmLeads;
    }

    private void setTDMLeads(LeaderBoard tdmLeads) {
        this.tdmLeads = tdmLeads;
        Log.i(TAG, "setTDMLeads : ");
    }

     public LeaderBoard getDuelLeads() {
        return duelLeads;
    }

    private void setDuelLeads(LeaderBoard duelLeads) {
        this.duelLeads = duelLeads;
        Log.i(TAG, "setDuelLeads : ");
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    private void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
        Log.i(TAG, "setPlayerStats : ");
    }

    public PlayerSummary getPlayerSummary() {
        return playerSummary;
    }

    private void setPlayerSummary(PlayerSummary playerSummary) {
        this.playerSummary = playerSummary;
        Log.i(TAG, "setPlayerSummary : ");
    }

    // рефлексия и дженерики сделать?

    public void parseDataGlobal(String jsonString) {
        if (jsonString != null) {
            DataGlobal data = gson.fromJson(jsonString, DataGlobal.class);
            Log.i(TAG, "String Object global: " + data.getTotalChampionusage() + "\n");
            setDataGlobal(data);
        }
    }

    public void parseLeaderBoard(String jsonString, Boolean mode) {
        if (jsonString != null) {
            LeaderBoard data = gson.fromJson(jsonString, LeaderBoard.class);
            Log.i(TAG, "String Object tdm: " + data.toString());
            if (mode) setDuelLeads(data);
            else setTDMLeads(data);
        }
    }

    public void parsePlayerSummary(String jsonString) {
        if (jsonString != null) {
            PlayerSummary data = gson.fromJson(jsonString, PlayerSummary.class);
            Log.i(TAG, "String Object summary: " + data.toString());
            setPlayerSummary(data);
        }
    }

    public void parsePlayerStats(String jsonString) {
        if (jsonString != null) {
            PlayerStats data = gson.fromJson(jsonString, PlayerStats.class);
            Log.i(TAG, "String Object stats: " + data.toString());
            data.getPlayerProfileStats().generateAll();     //генерируем чемпиона ALL
            setPlayerStats(data);
        }
    }

    public String[] parseSearchResult(String jsonString) {
        String[] result = null;
        if (jsonString != null && jsonString.length() > 0) {
            try {
                JsonParser jsonParser = new JsonParser();
                JsonArray a = (JsonArray) jsonParser.parse(jsonString);
                result = new String[a.size()];
                for (int i = 0; i < a.size(); i++) { //&& i<=20
                    JsonObject o = (JsonObject) a.get(i);
                    JsonPrimitive p = (JsonPrimitive) o.get("entityName");
                    String s = p.getAsString();
                    result[i] = s;
                } // Log.i(TAG, "result: " + result[0] + " , " + result[1]);
            } catch (ClassCastException e) {
                Log.i(TAG, "Incoming error ! List of searched player parsing failed : " + e );
            }
        }
        return result;
    }

}
