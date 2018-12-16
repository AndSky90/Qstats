package com.i550.qstats;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.qstats.Adapters.DataTranslator;

import java.util.HashSet;
import java.util.Set;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "qStatser";
    public static final String PREFS = "prefs";
    public static final String PROFILE_NAMES_LIST = "namelist";
    public static final String LAST_PROFILE_NAME = "last_profile_name";
    private static String profileName;
    private static String searchName = "";
    private String[] searchResult;
    private Set<String> profileNamesList = new HashSet<>();
    private SimpleCursorAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private DataTranslator dta;
    private TabLayout tabLayout;
    private ViewPagerAdapter vpa;
    private DrawerLayout mDrawerLayout;

    private ImageView statusStripe;

    private int[] tabIcons = {
            R.drawable.ic_champions,
            R.drawable.ic_medals,
            R.drawable.ic_modes,
            R.drawable.ic_weapons,
            R.drawable.ic_matches};

    MyViewModel mViewModel = new MyViewModel(getApplication());

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusStripe = findViewById(R.id.status_stripe);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        createQueryAdapter();


        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);
        viewPager.setAdapter(vpa);
       // configureTabLayout();
        tabLayout.setupWithViewPager(viewPager,false);          //false для того чтобы иконки не исчезали
        configureTabLayout();


        readSharedPreferences();
        if (profileName!=null) refreshData(profileName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh: {
                refreshData(null);               ///////////////////
                break;
            }
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
        //   return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        final View np = findViewById(R.id.header_nameplate);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(searchResult[position], true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                np.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                np.setVisibility(View.VISIBLE);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                {
                    profileName = query;
                    refreshData(profileName);                                ///////////////////
                }
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query != null && query.length() > 0) {
                    searchName = query;
                    new AsyncTaskNameSearch().execute();
                }
                return true;
            }
        });

        NavigationView navigationView = findViewById(R.id.navigation_menu_view);
        //TODO intents //menuItem.setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.url_stats:
                        if (checkInternet())
                            return true;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


        return true;        //end onCreateOptionsMenu
    }

    private void populateAdapter() {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "name"});
        for (int i = 0; i < searchResult.length; i++) {
            c.addRow(new Object[]{i, searchResult[i]});
        }
        mAdapter.changeCursor(c);
    }

    public void setHeaderColorActualData(boolean dataIsActual) {
        if (dataIsActual)
            statusStripe.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        else statusStripe.setBackgroundColor(getResources().getColor(R.color.colorRed));
    }

    private void refreshData(String name) {                                               ///////////////////
        if (name == null) {
            profileName = name;
            profileNamesList.add(name);
            Log.i(TAG, " NEW NAME: " + profileName);
        }
        if (checkInternet()) new AsyncTaskGlobal().execute();
    }


    private Boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void createQueryAdapter() {
        final String[] from = new String[]{"name"};
        final int[] to = new int[]{android.R.id.text1};
        mAdapter = new
                SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    protected void configureTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            // tabLayout. TODO посмотреть разные методы, с иконкой намутить
        }
    }

    private void readSharedPreferences() {
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (mSharedPreferences.contains(LAST_PROFILE_NAME)) {
            profileName = mSharedPreferences.getString(LAST_PROFILE_NAME, null);
        } /*else {
            profileName = "rapha";
        }*/

        if (mSharedPreferences.contains(PROFILE_NAMES_LIST)) {
            profileNamesList = new HashSet<>();
            profileNamesList = mSharedPreferences.getStringSet(PROFILE_NAMES_LIST, new HashSet<String>());
            searchResult = profileNamesList.toArray(new String[profileNamesList.size()]);
        }
        Log.i(TAG, "Prefs: PROFILE_NAMES_LIST: " + profileName + " LAST_PROFILE_NAME: " + profileNamesList);
        if (mSharedPreferences.contains("DataGlobal")) { new AsyncTaskFetchFromCache().execute();
        }
    }

    private void configureHeader() {

        ImageView namePlateH = findViewById(R.id.namelpate);
        ImageView profileIconH = findViewById(R.id.profile_icon);
        ImageView rangeIconH = findViewById(R.id.range_icon);
        TextView profileNameH = findViewById(R.id.profile_name);
        TextView duelElo = findViewById(R.id.duel_elo);

        dta = DataTranslator.getInstance(this);
        profileNameH.setText(profileName);
        int elo = mViewModel.getPlayerStats().getPlayerRatings().getDuelRating();
        duelElo.setText(String.valueOf(elo));
        rangeIconH.setImageDrawable(dta.getRangeImageTranslator(elo));
        profileIconH.setImageDrawable(dta.getIconsImageTranslator(mViewModel.getPlayerStats().getPlayerLoadOut().getIconId()));
        namePlateH.setImageDrawable(dta.getNameplatesImageTranslator(mViewModel.getPlayerStats().getPlayerLoadOut().getNamePlateId()));
    }



    /*
    public void updateView(){
        viewPager.invalidate();
        viewPager.updateViewLayout();
    }*/

//__________________________________________________________________________________________________

    class AsyncTaskGlobal extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setHeaderColorActualData(false);
        }
        @Override
        protected Void doInBackground(Void... voids) {

            NetQStatsWork background = new NetQStatsWork();
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
            mViewModel.emptyDb = false;

            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putStringSet(PROFILE_NAMES_LIST, profileNamesList);
            editor.putString("DataGlobal", dg);
            editor.putString("TdmLeads", tdm);
            editor.putString("DuelLeads", duel);
            editor.putString("PlayerSummary", summary);
            editor.putString("PlayerStats", stats);
            editor.putString(LAST_PROFILE_NAME, profileName).apply();
            editor.apply();

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            vpa.notifyDataSetChanged();
          //  configureTabLayout();
            configureHeader();
            setHeaderColorActualData(true);
        }
    }

    class AsyncTaskFetchFromCache extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            mViewModel.fetchDataGlobal(mSharedPreferences.getString("DataGlobal", null));
            mViewModel.fetchLeaderBoard(mSharedPreferences.getString("TdmLeads", null), false);
            mViewModel.fetchLeaderBoard(mSharedPreferences.getString("DuelLeads", null), true);
            mViewModel.fetchPlayerSummary(mSharedPreferences.getString("PlayerSummary", null));
            mViewModel.fetchPlayerStats(mSharedPreferences.getString("PlayerStats", null));
            mViewModel.emptyDb = false;
            Log.i(TAG, "Prefs: READ DATA : " );
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            vpa.notifyDataSetChanged();
          //  configureTabLayout();
            configureHeader();
        }
    }

    class AsyncTaskNameSearch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            NetQStatsWork background = new NetQStatsWork();
            String nameSearchJson = background.fetch(getString(R.string.url_player_search) + "?term=" + searchName);
            if (nameSearchJson != null) {
                Log.i(TAG, " Input search: " + searchName + ". NameSuggestion: " + nameSearchJson);
                String[] r = mViewModel.fetchSearchResult(nameSearchJson);
                if (r.length > 0) searchResult = r;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            populateAdapter();
        }
    }
}

