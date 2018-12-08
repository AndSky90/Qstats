package com.i550.qstats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

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

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter vpa;

    private DrawerLayout mDrawerLayout;


    private ImageView statusStripe;
    private Toolbar toolbar;
    private AppBarLayout headerLayout;
    private ConstraintLayout statusBar;
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

        toolbar = findViewById(R.id.toolbar_layout);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        statusStripe = findViewById(R.id.status_stripe);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.url_stats:
                        if (CheckInternet()) // TODO intents ;
                            break;
                }
                return true;
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(vpa);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        configureTabLayout();

      /*  headerLayout = findViewById(R.id.header_nameplate);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click header");
                if (CheckInternet()) new AsyncTaskGlobal().execute();
            }
        });*/

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
    public void updateView(){
        viewPager.invalidate();
        viewPager.updateViewLayout();
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

    public void setHeaderColorActualData() {
        if (DATA_IS_ACTUAL)
            statusStripe.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        else statusStripe.setBackgroundColor(getResources().getColor(R.color.colorBad));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh: {
                if (CheckInternet()) new AsyncTaskGlobal().execute();
                break;
            }
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
               // return true;
            }

        }
        return true;
     //   return super.onOptionsItemSelected(item);
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



