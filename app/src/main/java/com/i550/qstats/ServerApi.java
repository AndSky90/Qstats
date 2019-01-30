package com.i550.qstats;

import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;

import retrofit2.http.GET;
import retrofit2.Call;

import java.util.List;



public interface ServerApi {


    //    https://stats.quake.com/api/v2/
//      Global
//      Leaderboard?from=0&board=duel&season=current
//      Leaderboard?from=0&board=tdm&season=current
// Player/Stats?name=XF8ShaggyStoned
// Player/GamesSummary?name=XF8ShaggyStoned
// Player/Search?term=XF



    @GET("Global")
    DataGlobal getGlobal();

    @GET("Leaderboard?from=0&board=duel&season=current")
    LeaderBoard getDuel();

    @GET("Leaderboard?from=0&board=tdm&season=current")
    LeaderBoard getTdm();

    @GET("getGamesSummary")
    PlayerSummary getGamesSummary();

    @GET("getStats")
    PlayerStats getStats();

    @GET("Search")
    String[] Search();

}


