package com.i550.qstats;

import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.NameSearchEntity;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;

import retrofit2.http.*;
import retrofit2.Call;

import java.util.ArrayList;


public interface ServerApi {

    @GET("Global")
    Call<DataGlobal> callDataGlobal();

    @GET("Leaderboard?from=0&board=duel&season=current")
    Call<LeaderBoard> callDuelLeaderBoard();

    @GET("Leaderboard?from=0&board=tdm&season=current")
    Call<LeaderBoard> callTdmLeaderBoard();

    @GET("Player/GamesSummary")
    Call<PlayerSummary> callPlayerSummary(@Query("name") String queryName);;

    @GET("Player/Stats")
    Call<PlayerStats> callPlayerStats(@Query("name") String queryName);

    @GET("Player/Search")
    Call<ArrayList<NameSearchEntity>> callPlayerSearch(@Query("term") String queryName);

}


