package com.i550.qstats.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
                                                                                // может переделать в статик вместо синглтона?
public class AssetDataTranslator {                                                   //класс преобразует исходные данные в Title и Drawable для вывода на экран
    private Map<String, String> gameModeTitleTranslator;
    private Map<String, String> mapTitleTranslator;
    private Map<String, Drawable> mapImageTranslator;
    private Map<String, Drawable> gameModeImageTranslator;
    private Map<String, Drawable> championsImageTranslator;
    private ArrayList<Drawable> weaponsImageTranslatorIterable;
    private LinkedHashMap<String, Drawable> medalsImageTranslator;
    private ArrayList<Drawable> rangeImageTranslator;
    private Map<String, Drawable> nameplatesImageTranslator;
    private Map<String, Drawable> iconsImageTranslator;

    private static AssetDataTranslator translator;

    private AssetDataTranslator(){}

    public static AssetDataTranslator getInstance(Context context) {
        if (translator == null) {
            translator = new AssetDataTranslator(context);
        }
        return translator;
    }

    private AssetDataTranslator(Context context) {

        AssetManager mAssetManager = context.getAssets();

        gameModeTitleTranslator = new HashMap<>(15, 1);          //loadfactor = 1 -> менять capacity если добавляется новое!
        mapTitleTranslator = new HashMap<>(15, 1);
        mapImageTranslator = new HashMap<>(15, 1);
        gameModeImageTranslator = new LinkedHashMap<>(15, 1);
        championsImageTranslator = new LinkedHashMap<>(17, 1);
        weaponsImageTranslatorIterable = new ArrayList<>(11);
        medalsImageTranslator = new LinkedHashMap<>(106, 1);                                  // упорядоченный вывод на экран!
        rangeImageTranslator = new ArrayList<>(20);
        nameplatesImageTranslator = new HashMap<>(77, 1);                            // ждать фикс на сайте (не все есть)
        iconsImageTranslator = new HashMap<>(190, 1);                                                             // ждать фикс на сайте (не все есть)

        gameModeTitleTranslator.put("GameModeAll", "All modes");
        gameModeTitleTranslator.put("GameModeFFA", "Deathmatch");
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
        gameModeTitleTranslator.put("GameModeAAARENA", "Aaarena");

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
        mapTitleTranslator.put("citadel", "Citadel");


        String[] files;             //преобразуем все ассеты в Drawable заранее, т.к. одновременно очень много выводится на экран
        InputStream inputStream;
        try {
            files = mAssetManager.list("map_images");
            for (String f : files) {
                inputStream = mAssetManager.open("map_images/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                mapImageTranslator.put(f.replace(".jpg", ""), d);
            }
            files = mAssetManager.list("game_mode");
            for (String f : files) {
                inputStream = mAssetManager.open("game_mode/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                gameModeImageTranslator.put(f.replace(".png", ""), d);
            }
            files = mAssetManager.list("weapons");
            for (String f : files) {
                inputStream = mAssetManager.open("weapons/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                weaponsImageTranslatorIterable.add(d);
            }
            files = mAssetManager.list("champions");
            for (String f : files) {
                inputStream = mAssetManager.open("champions/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                championsImageTranslator.put(f.replace(".png", ""), d);
            }
            files = mAssetManager.list("medals");
            for (String f : files) {
                inputStream = mAssetManager.open("medals/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                medalsImageTranslator.put(f.replace(".png", ""), d);
            }
            files = mAssetManager.list("range");
            for (String f : files) {
                inputStream = mAssetManager.open("range/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                rangeImageTranslator.add(d);
            }
            files = mAssetManager.list("nameplates");
            for (String f : files) {
                inputStream = mAssetManager.open("nameplates/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                nameplatesImageTranslator.put(f.replace(".png", ""), d);
            }
            files = mAssetManager.list("profile_icon");
            for (String f : files) {
                inputStream = mAssetManager.open("profile_icon/" + f);
                Drawable d = Drawable.createFromStream(inputStream, null);
                iconsImageTranslator.put(f.replace(".png", ""), d);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        mAssetManager = null;       //больше не нужен
    }


    String getMapTitleTranslation(String k) {
        return mapTitleTranslator.get(k);
    }

    Drawable getMapImageTranslation(String k) {
        return mapImageTranslator.get(k);
    }

    String getGameModeTitleTranslation(String k) {
        return gameModeTitleTranslator.get(k);
    }

    Drawable getGameModeImageTranslation(String k) {
        return gameModeImageTranslator.get(k);
    }

    Drawable getChampionsImageTranslation(String k) {
        return championsImageTranslator.get(k);
    }

    Drawable getWeaponsImageTranslationIterable(int index) {
        return weaponsImageTranslatorIterable.get(index);
    }

    Map<String, Drawable> getMedalsImageTranslator() {
        return medalsImageTranslator;
    }

    int getSizeMedalsList() {
        return medalsImageTranslator.size();
    }

    public Drawable getRangeImageTranslator(int elo) {          //подставляем нужную иконку рейтинга в зависимости от его значения
        int range = (elo - 775) / 75;
        if (elo < 775) range = 0;
        if (elo > 2200) range = 19;
        return rangeImageTranslator.get(range);
    }

    public Drawable getNameplatesImageTranslator(String k) {
        return nameplatesImageTranslator.get(k);
    }

    public Drawable getIconsImageTranslator(String k) {
        return iconsImageTranslator.get(k);
    }
}
