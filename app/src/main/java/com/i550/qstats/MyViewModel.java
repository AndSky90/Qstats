package com.i550.qstats;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;

import java.io.IOException;

import static com.i550.qstats.MainActivity.LOG_D_TAG;

//__________________________________________________________________________________________________
public class MyViewModel extends ViewModel {

    private static final String TAG = "qStatserViewModel";

    private Gson gson = new Gson();
    private MutableLiveData<DataGlobal> dataGlobal;
    private MutableLiveData<LeaderBoard> tdmLeaderBoard;
    private MutableLiveData<LeaderBoard> duelLeaderBoard;
    private MutableLiveData<PlayerStats> playerStats;
    private MutableLiveData<PlayerSummary> playerSummary;
    private MutableLiveData<MainActivity.RefreshState> refreshState;
    private Boolean refreshStateSomeDownloadError;
    private String profileName;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://stats.quake.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ServerApi retrofitApi = retrofit.create(ServerApi.class);


    public void refreshAllData(String query) {


        refreshStateSomeDownloadError = false;

        loadDataGlobal();
        loadTdmLeaderBoard();
        loadDuelLeaderBoard();

        if (refreshStateSomeDownloadError) return;


        if (query != null || profileName != null) {
            String oldName = profileName;
            if (profileName == null) {
                profileName = query;
            }

            loadPlayerStats();
          /*  if (!refreshStateSomeDownloadError) {
                profileName = oldName;
                return;
            }*/
            loadPlayerSummary();
          /*  if (!refreshStateSomeDownloadError) {
                profileName = oldName;
                return;
            }*/
        }

        refreshState.setValue(MainActivity.RefreshState.outdated);
        if (!refreshStateSomeDownloadError) {
            refreshState.setValue(MainActivity.RefreshState.actual);
        }

        // vpa.notifyDataSetChanged();                временно,заменится на RXJAVA zip observable
    }


    public LiveData<MainActivity.RefreshState> getRefreshState() {
        if (refreshState == null) {
            refreshState = new MutableLiveData<>();
            refreshState.setValue(MainActivity.RefreshState.outdated);
        }
        return refreshState;
    }

    public void setStateOfModel(MainActivity.RefreshState state) {
        refreshState.setValue(state);
    }


    public LiveData<DataGlobal> getDataGlobal() {
        if (dataGlobal == null) {
            dataGlobal = new MutableLiveData<>();
            loadDataGlobal();
        }
        return dataGlobal;
    }

    private void loadDataGlobal() {
        refreshState.setValue(MainActivity.RefreshState.update);
        Call<DataGlobal> call = retrofitApi.callDataGlobal();
        call.enqueue(new Callback<DataGlobal>() {
            @Override
            public void onResponse(Call<DataGlobal> call, Response<DataGlobal> response) {
                if (response.isSuccessful()) {
                    setDataGlobal(response.body());
                    Log.d(TAG, "dataGlobal onResponse response.body(): " + response.body());
                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "dataGlobal statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataGlobal> call, Throwable t) {
                Log.e(TAG, "DataGlobal onFailure: " + call);
                refreshStateSomeDownloadError = true;
            }
        });
    }

    public void setDataGlobal(DataGlobal data) {
        if (dataGlobal == null) {
            dataGlobal = new MutableLiveData<>();
        }
        dataGlobal.postValue(data);
    }

    public LiveData<LeaderBoard> getTdmLeaderBoard() {
        if (tdmLeaderBoard == null) {
            tdmLeaderBoard = new MutableLiveData<>();
            loadDataGlobal();
        }
        return tdmLeaderBoard;
    }

    private void loadTdmLeaderBoard() {

        Call<LeaderBoard> call = retrofitApi.callTdmLeaderBoard();
        call.enqueue(new Callback<LeaderBoard>() {
            @Override
            public void onResponse(Call<LeaderBoard> call, Response<LeaderBoard> response) {
                if (response.isSuccessful()) {
                    setTdmLeaderBoard(response.body());
                    Log.d(TAG, "tdmLeaderBoard onResponse response.body(): " + response.body());
                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "tdmLeaderBoard statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeaderBoard> call, Throwable t) {
                Log.e(TAG, "tdmLeaderBoard onFailure: " + call);
                refreshStateSomeDownloadError = true;
            }
        });
    }

    public void setTdmLeaderBoard(LeaderBoard data) {
        if (tdmLeaderBoard == null) {
            tdmLeaderBoard = new MutableLiveData<>();
        }
        tdmLeaderBoard.postValue(data);
    }

    public LiveData<LeaderBoard> getDuelLeaderBoard() {
        if (duelLeaderBoard == null) {
            duelLeaderBoard = new MutableLiveData<>();
            loadDataGlobal();
        }
        return duelLeaderBoard;
    }

    private void loadDuelLeaderBoard() {

        Call<LeaderBoard> call = retrofitApi.callDuelLeaderBoard();
        call.enqueue(new Callback<LeaderBoard>() {
            @Override
            public void onResponse(Call<LeaderBoard> call, Response<LeaderBoard> response) {
                if (response.isSuccessful()) {
                    setDuelLeaderBoard(response.body());
                    Log.d(TAG, "duelLeaderBoard onResponse response.body(): " + response.body());
                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "duelLeaderBoard statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeaderBoard> call, Throwable t) {
                Log.e(TAG, "duelLeaderBoard onFailure: " + call);
                refreshStateSomeDownloadError = true;
            }
        });
    }

    public void setDuelLeaderBoard(LeaderBoard data) {
        if (duelLeaderBoard == null) {
            duelLeaderBoard = new MutableLiveData<>();
        }
        duelLeaderBoard.postValue(data);
    }

    public LiveData<PlayerStats> getPlayerStats() {
        if (playerStats == null) {
            playerStats = new MutableLiveData<>();
            loadDataGlobal();
        }
        return playerStats;
    }

    private void loadPlayerStats() {

        Call<PlayerStats> call = retrofitApi.callPlayerStats(profileName);
        call.enqueue(new Callback<PlayerStats>() {
            @Override
            public void onResponse(Call<PlayerStats> call, Response<PlayerStats> response) {
                if (response.isSuccessful()) {
                    setPlayerStats(response.body());
                    Log.d(TAG, "playerStats onResponse response.body(): " + response.body());

                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "playerStats statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlayerStats> call, Throwable t) {
                Log.e(TAG, "playerStats onFailure: " + call);
                refreshStateSomeDownloadError = true;
            }
        });
    }

    public void setPlayerStats(PlayerStats data) {
        if (playerStats == null) {
            playerStats = new MutableLiveData<>();
        }
        playerStats.postValue(data);
    }

    public LiveData<PlayerSummary> getPlayerSummary() {
        if (playerSummary == null) {
            playerSummary = new MutableLiveData<>();
            loadDataGlobal();
        }
        return playerSummary;
    }

    private void loadPlayerSummary() {
        Call<PlayerSummary> call = retrofitApi.callPlayerSummary(profileName);
        call.enqueue(new Callback<PlayerSummary>() {
            @Override
            public void onResponse(Call<PlayerSummary> call, Response<PlayerSummary> response) {
                if (response.isSuccessful()) {
                    setPlayerSummary(response.body());
                    Log.d(TAG, "playerSummary onResponse response.body(): " + response.body());
                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "playerSummary statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlayerSummary> call, Throwable t) {
                Log.e(TAG, "playerSummary onFailure: " + call);
                refreshStateSomeDownloadError = true;
            }
        });
    }

    public void setPlayerSummary(PlayerSummary data) {
        if (playerSummary == null) {
            playerSummary = new MutableLiveData<>();
        }
        playerSummary.postValue(data);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public void parseDataGlobal(String jsonString) {
        if (jsonString != null) {
            DataGlobal data = gson.fromJson(jsonString, DataGlobal.class);
            Log.i(LOG_D_TAG, "String Object global: " + data.getTotalChampionusage() + "\n");
            setDataGlobal(data);
        }
    }

    public void parseLeaderBoard(String jsonString, Boolean mode) {
        if (jsonString != null) {
            LeaderBoard data = gson.fromJson(jsonString, LeaderBoard.class);
            Log.i(LOG_D_TAG, "String Object tdm: " + data.toString());
            if (mode) setDuelLeaderBoard(data);
            else setTdmLeaderBoard(data);
        }
    }

    public void parsePlayerSummary(String jsonString) {
        if (jsonString != null) {
            PlayerSummary data = gson.fromJson(jsonString, PlayerSummary.class);
            Log.i(LOG_D_TAG, "String Object summary: " + data.toString());
            setPlayerSummary(data);
        }
    }

    public void parsePlayerStats(String jsonString) {
        if (jsonString != null) {
            PlayerStats data = gson.fromJson(jsonString, PlayerStats.class);
            Log.i(LOG_D_TAG, "String Object stats: " + data.toString());
            data.getPlayerProfileStats().generateAll();     //генерируем чемпиона ALL
            setPlayerStats(data);
        }
    }

 /*   public String[] parseSearchResult(String jsonString) {
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
                } // Log.i(LOG_D_TAG, "result: " + result[0] + " , " + result[1]);
            } catch (ClassCastException e) {
                Log.i(LOG_D_TAG, "Incoming error ! List of searched player parsing failed : " + e );
            }
        }
        return result;
    }*/

}