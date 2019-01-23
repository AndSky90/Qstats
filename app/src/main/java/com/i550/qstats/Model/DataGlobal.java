package com.i550.qstats.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class DataGlobal {
    public DataGlobal() { }

    @SerializedName("total_championusage")
    private Map<String, String> totalChampionusage = new HashMap<>();

    @SerializedName("total_deaths")
    private Map<String, String> totalDeaths = new HashMap<>();

    @SerializedName("change_championusage")
    private Map<String, String> changeChampionusage = new HashMap<>();

    @SerializedName("change_deaths")
    private Map<String, String> changeDeaths = new HashMap<>();


    public Map<String, String> getTotalChampionusage() {
        return totalChampionusage;
    }

    public Map<String, String> getTotalDeaths() {
        return totalDeaths;
    }

    public Map<String, String> getChangeChampionusage() {
        return changeChampionusage;
    }

    public Map<String, String> getChangeDeaths() {
        return changeDeaths;
    }

}
