package com.i550.qstats.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTranslationAdapters {
    private Map<String, String> gameModeTitleTranslator;
    private Map<String, String> mapTitleTranslator;
    private Map<String, Drawable> mapImageTranslator;
    private Map<String, Drawable> gameModeImageTranslator;
    private Map<String, Drawable> weaponsImageTranslator;
    private Map<String, Drawable> championsImageTranslator;
    private List<Drawable> championsImageTranslatorIterable;

    private Context context;
    private static DataTranslationAdapters translator;

    /*
    public DataTranslationAdapters init(Context context) {
        return new DataTranslationAdapters(context);
    }*/

    private DataTranslationAdapters(Context context) {

        gameModeTitleTranslator = new HashMap<>();
        mapTitleTranslator = new HashMap<>();
        mapImageTranslator = new HashMap<>();
        gameModeImageTranslator = new HashMap<>();
        weaponsImageTranslator = new HashMap<>();
        championsImageTranslator = new HashMap<>();


        gameModeTitleTranslator.put("GameModeFFA", "Free for all");
        gameModeTitleTranslator.put("GameModeTeamDeathmatch", "Team Deathmatch");
        gameModeTitleTranslator.put("GameModeDuel", "Duel");
        gameModeTitleTranslator.put("GameModeObelisk", "Sacrifice");
        gameModeTitleTranslator.put("GameModeObeliskPro", "Sacrifice Pro");
        gameModeTitleTranslator.put("GameModeTeamDeathmatch2vs2", "TDM 2v2");
        gameModeTitleTranslator.put("GameModeInstagib", "Instagib");
        gameModeTitleTranslator.put("GameModeDuelPro", "Duel Pro");
        gameModeTitleTranslator.put("GameModeRocketfest", "Rocket Fest");
        gameModeTitleTranslator.put("GameModeObeliskMyst", "Obelisk Myst");
        gameModeTitleTranslator.put("GameModeHOLY_TRINITY", "Unholy Trinity");
        gameModeTitleTranslator.put("GameModeCtf", "Capture the flag");
        gameModeTitleTranslator.put("GameModeSlipgate", "Slipgate");


        mapTitleTranslator.put("awoken", "Awoken");
        mapTitleTranslator.put("blood_covenant", "Blood Covenant");
        mapTitleTranslator.put("bloodrun", "Blood Run");
        mapTitleTranslator.put("burial_chamber", "Burial Chamber");
        mapTitleTranslator.put("church", "Church of Azatoth");
        mapTitleTranslator.put("corrupted_keep", "Corrupted Keep");
        mapTitleTranslator.put("fortress_of_the_deep", "The Molten Falls");
        mapTitleTranslator.put("lighthouse", "Tempest Shrine");
        mapTitleTranslator.put("longestyard", "Longest Yard");
        mapTitleTranslator.put("ruins_of_sarnath", "Ruins of Sarnath");
        mapTitleTranslator.put("vale_of_pnath", "Vale of Pnath");
        mapTitleTranslator.put("lockbox", "Lockbox");


        AssetManager mAssetManager = context.getAssets();
        this.context = context;
        String[] files;
        try {
            files = mAssetManager.list("map_images");
            for (String f : files) {
                InputStream inputStream = mAssetManager.open("map_images/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                mapImageTranslator.put(f.replace(".jpg", ""), d);
            }
            files = mAssetManager.list("game_mode");
            for (String f : files) {
                InputStream inputStream = mAssetManager.open("game_mode/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                gameModeImageTranslator.put(f.replace(".png", ""), d);
            }
            files = mAssetManager.list("weapons");
            for (String f : files) {
                InputStream inputStream = mAssetManager.open("weapons/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                weaponsImageTranslator.put(f.replace(".png", ""), d);
            }
            files = mAssetManager.list("champions");
            for (String f : files) {
                InputStream inputStream = mAssetManager.open("champions/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                championsImageTranslator.put(f.replace(".png", ""), d);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        championsImageTranslatorIterable = new ArrayList<>(championsImageTranslator.values());
    }

    public static DataTranslationAdapters getInstance(Context context) {
        if (translator == null) {
            translator = new DataTranslationAdapters(context);
        }
        return translator;
    }

    public String getMapTitleTranslation(String k) {
        return mapTitleTranslator.get(k);
    }

    public Drawable getMapImageTranslation(String k) {
        return mapImageTranslator.get(k);
    }

    public String getGameModeTitleTranslation(String k) {
        return gameModeTitleTranslator.get(k);
    }

    public Drawable getGameModeImageTranslation(String k) {
        return gameModeImageTranslator.get(k);
    }

    public Drawable getWeaponsImageTranslation(String k) {
        return weaponsImageTranslator.get(k);
    }

    public Drawable getChampionsImageTranslation(String k) {
        return championsImageTranslator.get(k);
    }

}
