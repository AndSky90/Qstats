package com.i550.qstats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats;
import com.i550.qstats.Model.PlayerSummary;

//__________________________________________________________________________________________________

public class MyViewModel extends AndroidViewModel {
    public MyViewModel(@NonNull Application application) {
        super(application);
    }
    private static final String TAG = "qS";

    private static DataGlobal dataGlobal = new DataGlobal();
    private static LeaderBoard tdmLeads = new LeaderBoard();
    private static LeaderBoard duelLeads = new LeaderBoard();
    private static PlayerStats playerStats = new PlayerStats();
    private static PlayerSummary playerSummary = new PlayerSummary();

    public static DataGlobal getDataGlobal() {
        return dataGlobal;
    }
    public static void setDataGlobal(DataGlobal dataGlobal) {
        MyViewModel.dataGlobal = dataGlobal;
        Log.i(TAG, "Set Object: " + MyViewModel.dataGlobal.getTotal_championusage() +"\n" );
    }

    public static LeaderBoard getTdmLeads() {
        return tdmLeads;
    }
    public static void setTDMLeads(LeaderBoard tdmLeads) {
        MyViewModel.tdmLeads = tdmLeads;
    }

    public static LeaderBoard getDuelLeads() {
        return duelLeads;
    }
    public static void setDuelLeads(LeaderBoard duelLeads) {
        MyViewModel.duelLeads = duelLeads;
    }

    public static PlayerStats getPlayerStats() {
        return playerStats;
    }
    public static void setPlayerStats(PlayerStats playerStats) {
        MyViewModel.playerStats = playerStats;
    }

    public static PlayerSummary getPlayerSummary() {
        return playerSummary;
    }
    public static void setPlayerSummary(PlayerSummary playerSummary) {
        MyViewModel.playerSummary = playerSummary;
    }
}





/*
  public LiveData<DataGlobal> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadDataFromServer();
            Log.i(TAG, "String Object: " + data);
        }
        return data;
    }
*/
