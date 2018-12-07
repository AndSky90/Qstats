package com.i550.qstats;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.i550.qstats.Model.DataGlobal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "qStatser";
    public static final String PREFS = "prefs";
    public static final String PROFILE_NAMES_LIST = "namelist";
    public static final String LAST_PROFILE_NAME = "last_profile_name";
    public static final String LAST_DATA = "last data";
    private static String profileName;
    private Set<String> profileNamesList;
    private Boolean ADDED_NEW_PROFILE = false;
    private Boolean DATA_IS_ACTUAL = false;

    MyViewModel mViewModel = new MyViewModel(getApplication());
    SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public ViewPagerAdapter vpa;
    public AppBarLayout headerLayout;
    public ImageView statusBar;
    private int[] tabIcons = {
            R.drawable.ic_champions,
            R.drawable.ic_medals,
            R.drawable.ic_modes,
            R.drawable.ic_weapons,
            R.drawable.ic_matches,
            R.drawable.ic_compare};




    protected void configureTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            // tabLayout. TODO посмотреть разные методы, с иконкой намутить
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle sis) {
        super.onSaveInstanceState(sis);
        sis.putString("profileName", profileName);
        Log.d(TAG, "onSaveInstanceState profileName");
    }

    @Override
    protected void onRestoreInstanceState(Bundle sis) {
        super.onRestoreInstanceState(sis);
        profileName = sis.getString("profileName");
        Log.d(TAG, "onRestoreInstanceState");
    }

  /*  @Override
    protected void onStop() {
        if (ADDED_NEW_PROFILE) {
            editor = mSharedPreferences.edit();
            editor.putStringSet(PROFILE_NAMES_LIST, profileNamesList);
            editor.putString(LAST_PROFILE_NAME, profileName).apply();
        }
        super.onStop();
    }*/

    protected void queryNewProfile() {
        profileName = "XF8ShaggyStoned";
        ADDED_NEW_PROFILE = true;
        profileNamesList.add(profileName);
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(vpa);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        configureTabLayout();
        statusBar = findViewById(R.id.status_bar_colored);
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click header");
                if (CheckInternet()) new AsyncTaskGlobal().execute();
            }
        });

        readSharedPreferences();

        queryNewProfile();

        if (CheckInternet()) new AsyncTaskGlobal().execute();

    }

    private Boolean CheckInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




    /*
        TestViewModel testModel = ViewModelProviders.of(this).get(TestViewModel.class);
        Test te = new Test();
        te.setJopa("aage2324geg");
        testModel.setTest(te);


    public void updateView(){
        viewPager.invalidate();
        // viewPager.updateViewLayout();
    }*/


    private void readSharedPreferences() {
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        profileNamesList = new HashSet<>();
        if (mSharedPreferences.contains(PROFILE_NAMES_LIST)) {
            profileNamesList = mSharedPreferences.getStringSet(PROFILE_NAMES_LIST, new HashSet<String>());
        }
        if (mSharedPreferences.contains(LAST_PROFILE_NAME)) {
            profileName = mSharedPreferences.getString(LAST_PROFILE_NAME, null);
        } else queryNewProfile();
        Log.i(TAG, "Prefs: PROFILE_NAMES_LIST: " + profileName + " LAST_PROFILE_NAME: " + profileNamesList);
      /*  if (mSharedPreferences.contains("DataGlobal")) {
            mViewModel.fetchDataGlobal(mSharedPreferences.getString("DataGlobal", null));
            mViewModel.fetchLeaderBoard(mSharedPreferences.getString("TdmLeads", null), false);
            mViewModel.fetchLeaderBoard(mSharedPreferences.getString("DuelLeads", null), true);
            mViewModel.fetchPlayerSummary(mSharedPreferences.getString("PlayerSummary", null));
            mViewModel.fetchPlayerStats(mSharedPreferences.getString("PlayerStats", null));
        }*/
    }
    public void setHeaderColorActualData(){
        if(DATA_IS_ACTUAL)             statusBar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        else             statusBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

//__________________________________________________________________________________________________

    class AsyncTaskGlobal extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DATA_IS_ACTUAL = false;
            setHeaderColorActualData();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            NetQstatsWork background = new NetQstatsWork();
            String dg = background.fetch(getString(R.string.url_global));
            String tdm = background.fetch(getString(R.string.url_tdm_leads));
            String duel = background.fetch(getString(R.string.url_duel_leads));
            String summary = background.fetch(getString(R.string.url_player_summary) + "?name=" + profileName);
            String stats = background.fetch(getString(R.string.url_player_stats) + "?name=" + profileName);


            mViewModel.fetchDataGlobal(dg);
            mViewModel.fetchLeaderBoard(tdm, false);
            mViewModel.fetchLeaderBoard(duel, true);
            mViewModel.fetchPlayerSummary(summary);
            mViewModel.fetchPlayerStats(stats);

            editor = mSharedPreferences.edit();

            editor.putString("DataGlobal", dg);
            editor.putString("TdmLeads", tdm);
            editor.putString("DuelLeads", duel);
            editor.putString("PlayerSummary", summary);
            editor.putString("PlayerStats", stats);
            editor.apply();

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            vpa.notifyDataSetChanged();
            configureTabLayout();
            DATA_IS_ACTUAL = true;
            setHeaderColorActualData();
        }
    }



/*
    public class AsyncTaskNameSearch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            NetQstatsWork background = new NetQstatsWork();

            background.fetchNames(getApplication().getString(R.string.url_global), input);

            return null;
        }

        @Override
        protected void onPostExecute(Void r) {

        }
    }*/


}
