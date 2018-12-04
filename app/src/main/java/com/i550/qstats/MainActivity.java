package com.i550.qstats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "qStatser";
    public static final String PROFILE_NAMES_LIST = "namelist";
    public static final String LAST_PROFILE_NAME= "last_profile_name";
    private static String profileName;

    private Set<String> profileNamesList;
    private Boolean ADDED_NEW_PROFILE = false;
    private SharedPreferences mSharedPreferences;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public ViewPagerAdapter vpa;
    private AppBarLayout headerLayout;
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

    @Override
    protected void onStop() {
        if (ADDED_NEW_PROFILE) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putStringSet(PROFILE_NAMES_LIST, profileNamesList).apply();
            editor.putString(LAST_PROFILE_NAME, profileName).apply();
            super.onStop();
        }
    }

    protected void queryNewProfile() {
        profileName = "XF8ShaggyStoned";
        if(!profileNamesList.contains(profileName)) profileNamesList.add(profileName);
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


        mSharedPreferences = getSharedPreferences(PROFILE_NAMES_LIST, Context.MODE_PRIVATE);
        profileNamesList = new HashSet<>();
        if (mSharedPreferences.contains(PROFILE_NAMES_LIST)) {
            profileNamesList = mSharedPreferences.getStringSet(PROFILE_NAMES_LIST, new HashSet<String>());
        }
        mSharedPreferences = getSharedPreferences(LAST_PROFILE_NAME, Context.MODE_PRIVATE);
        profileName = mSharedPreferences.getString(LAST_PROFILE_NAME,profileName);
        if(profileName==null) queryNewProfile();
        Log.i(TAG, "Prefs: PROFILE_NAMES_LIST: " + profileName + " LAST_PROFILE_NAME: " + profileNamesList );


        updateGlobalDataFromServer();


      /*


            headerLayout = findViewById(R.id.headerLayout);
            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "Click header");

                }
            });

        TestViewModel testModel = ViewModelProviders.of(this).get(TestViewModel.class);
        Test te = new Test();
        te.setJopa("aage2324geg");
        testModel.setTest(te);*/

    }

    /*public void updateView(){
        viewPager.invalidate();
        // viewPager.updateViewLayout();
    }*/

    public void updateGlobalDataFromServer() {
        new AsyncTaskGlobal().execute();
    }

//__________________________________________________________________________________________________

    class AsyncTaskGlobal extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            NetQstatsWork background = new NetQstatsWork();

            background.fetchDataGlobal(getString(R.string.url_global));
            background.fetchLeaderBoard(getString(R.string.url_tdm_leads), false);
            background.fetchLeaderBoard(getString(R.string.url_duel_leads), true);
            background.fetchPlayerSummary(getString(R.string.url_player_summary), profileName);
            //       background.fetchPlayerStats(getString(R.string.url_player_stats), profileName);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            vpa.notifyDataSetChanged();
            configureTabLayout();
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
