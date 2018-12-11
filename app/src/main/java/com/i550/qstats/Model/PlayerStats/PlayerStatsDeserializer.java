package com.i550.qstats.Model.PlayerStats;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;

import java.lang.reflect.Type;
import java.util.List;

public class PlayerStatsDeserializer implements JsonDeserializer<PlayerStats> {

    //  JSONObject userJson = new JSONObject(string);


    @Override
    public PlayerStats deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final String TAG = "qStatserViewModel";
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        JsonObject jsonObject = json.getAsJsonObject();

        PlayerStats playerStats = new PlayerStats();

        playerStats.setName(jsonObject.get("name").getAsString());

        JsonObject r = jsonObject.get("playerRatings").getAsJsonObject();
        PlayerRatings playerRatings = gson.fromJson(r, PlayerRatings.class);
        playerStats.setPlayerRatings(playerRatings);
        Log.i(TAG, "String Object global: " + playerStats.getPlayerRatings() + "\n");

        JsonObject l = jsonObject.get("playerLoadOut").getAsJsonObject();
        PlayerLoadOut playerLoadOut = gson.fromJson(l, PlayerLoadOut.class);
        playerStats.setPlayerLoadOut(playerLoadOut);
        Log.i(TAG, "String Object global: " + playerStats.getPlayerLoadOut() + "\n");


        JsonObject stats = jsonObject.get("playerProfileStats").getAsJsonObject();
        stats = stats.get("champions").getAsJsonObject();
        JsonObject ranger = stats.get("RANGER").getAsJsonObject();
        Champions RANGER = gson.fromJson(ranger, Champions.class);

        JsonObject scalebearer = stats.get("SCALEBEARER").getAsJsonObject();
        JsonObject visor = stats.get("VISOR").getAsJsonObject();
        JsonObject anarki = stats.get("ANARKI").getAsJsonObject();
        JsonObject nyx = stats.get("NYX").getAsJsonObject();
        JsonObject sorlag = stats.get("SORLAG").getAsJsonObject();
        JsonObject clutch = stats.get("CLUTCH").getAsJsonObject();
        JsonObject galena = stats.get("GALENA").getAsJsonObject();
        JsonObject slash = stats.get("SLASH").getAsJsonObject();
        JsonObject doom_slayer = stats.get("DOOM_SLAYER").getAsJsonObject();
        JsonObject bj_blazkowicz = stats.get("BJ_BLAZKOWICZ").getAsJsonObject();
        JsonObject keel = stats.get("KEEL").getAsJsonObject();
        JsonObject strogg = stats.get("STROGG").getAsJsonObject();
        JsonObject death_knight = stats.get("DEATH_KNIGHT").getAsJsonObject();
        JsonObject athena = stats.get("ATHENA").getAsJsonObject();
        JsonObject eisen = stats.get("EISEN").getAsJsonObject();




        //     PlayerProfileStats playerProfileStats = gson.fromJson(stats, PlayerProfileStats.class);
        //     playerStats.setPlayerProfileStats(playerProfileStats);
        //     Log.i(TAG, "String Object global: " + playerStats.getPlayerProfileStats() + "\n");


        JsonObject state = jsonObject.get("playerLevelState").getAsJsonObject();
        PlayerLevelState playerLevelState = gson.fromJson(state, PlayerLevelState.class);
        playerStats.setPlayerLevelState(playerLevelState);
        Log.i(TAG, "String Object global: " + playerStats.getPlayerLevelState() + "\n");

        JsonArray m = jsonObject.get("matches").getAsJsonArray();
        List<Matches> matches = null;
        for (JsonElement match:m){
            matches.add(gson.fromJson(match,Matches.class));
        }
        playerStats.setMatches(matches);




/*          Gson gson = new Gson();

            BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);

            dwarf.setDwarfAge(jsonObject.get("age").getAsInt());

            dwarf.setFacialHair((FacialHair) context.deserialize(jsonObject.get("facialHair"), FacialHair.class));

            JsonArray weapons = jsonObject.getAsJsonArray("weapons");
            for(JsonElement weapon : weapons) {
                if(weapon.isJsonPrimitive()) {
                    dwarf.addWeapon(new Weapon(weapon.getAsString()));
                } else {
                    dwarf.addWeapon((UniqueWeapon) context.deserialize(weapon, UniqueWeapon.class));
                }
            }
*/
        return playerStats;
    }
}