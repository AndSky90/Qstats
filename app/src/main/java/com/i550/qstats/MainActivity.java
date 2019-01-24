package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.qstats.Adapters.DataTranslator;

import java.util.HashSet;
import java.util.Set;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity implements OnChangeNameFromLeaderList {

    private static MainActivity instanceMainActivity;
    private static final String TAG = "qStats";
    public static final String PREFS = "prefs";
    public static final String PROFILE_NAMES_LIST = "name_list";
    public static final String LAST_PROFILE_NAME = "last_profile_name";
    private static String profileName;
    private static String searchName;
    private String[] searchResult;
    private static Set<String> profileNamesList = new HashSet<>();

    private SimpleCursorAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private DataTranslator dta;
    private TabLayout tabLayout;
    private ViewPagerAdapter vpa;
    private DrawerLayout mDrawerLayout;
    private SearchView searchView;
    private Toolbar toolbar;

    AsyncTaskGlobal mAsyncTaskGlobal;                                       // для работы асинктаска с configchanges
    AsyncTaskNameSearch mAsyncTaskNameSearch;
    AsyncTaskParseFromCache mAsyncTaskParseFromCache;

    public enum RefreshMode {update, actual, outdated}

    MenuItem refreshItem;
    private int[] tabIcons = {
            R.drawable.ic_champions,
            R.drawable.ic_modes,
            R.drawable.ic_medals,
            R.drawable.ic_weapons,
            R.drawable.ic_matches};

    MyViewModel mViewModel;

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mAsyncTaskGlobal;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanceMainActivity = this;     //сохраняем ссылку для асинктасков

        MainActivity.this.setTitle("");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.tab_layout);
        dta = DataTranslator.getInstance(this);

        ViewPager viewPager = findViewById(R.id.viewpager);

        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        createQueryAdapter();

        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);
        viewPager.setAdapter(vpa);

        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        readSharedPreferences();

        tabLayout.setupWithViewPager(viewPager, false);          //false для того чтобы иконки не исчезали
        configureTabLayout();

        mAsyncTaskGlobal = (AsyncTaskGlobal) getLastCustomNonConfigurationInstance();
        Log.w("qStatsGlobalAsynctask", "getLastCustomNonConfigurationInstance " + (mAsyncTaskGlobal != null ? mAsyncTaskGlobal.hashCode() : " null object")
                + ", MainActivity: " + this.hashCode());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                              // inflate toolbar & searchView

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        refreshItem = menu.findItem(R.id.menu_refresh);
        final View np = findViewById(R.id.header_nameplate);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setSuggestionsAdapter(mAdapter);         // on first launch searchview is open

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
        });         // onClick suggestion
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                {
                    refreshData(query);
                }
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query != null && query.length() > 0) {
                    searchName = query;
                    if (checkInternet()) {
                        mAsyncTaskNameSearch = new AsyncTaskNameSearch();
                        mAsyncTaskNameSearch.execute();
                    }
                }
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
        // if (profileName==null) menuItem.expandActionView();             // activate if intended -> searchview is open on first launch
        NavigationView navigationView = findViewById(R.id.navigation_menu_view);
        navigationView.setNavigationItemSelectedListener(MenuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.url_stats:
                    if (checkInternet())
                        return true;
            }
            mDrawerLayout.closeDrawers();
            return true;
        });             // onClick navigation_menu each item TODO intents //menuItem.setChecked(true);

        if (mAsyncTaskGlobal != null) mAsyncTaskGlobal.link();
        refreshData(null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("qStats", "MenuItem ID pressed: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.menu_refresh: {
                refreshData(null);
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

    void refreshData(String name) {
        if (name != null) searchName = name;
        else searchName = profileName;
        if (checkInternet()) {

            if (mAsyncTaskGlobal == null) {
                mAsyncTaskGlobal = new AsyncTaskGlobal();
                mAsyncTaskGlobal.link();
                mAsyncTaskGlobal.execute();
            }
        }
    }

    @Override
    public void OnChangeName(String name) {
        refreshData(name);
    }

    public void setRefreshIcon(RefreshMode refreshMode) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotation);
        FrameLayout iconView;
        MenuItem RefrItem = toolbar.getMenu().findItem(R.id.menu_refresh);
        switch (refreshMode) {
            case update: {
                iconView = (FrameLayout) inflater.inflate(R.layout.refresh_action_view, null);
                iconView.startAnimation(rotation);
                RefrItem.setActionView(iconView);
                break;
            }
            case actual: {
                iconView = (FrameLayout) inflater.inflate(R.layout.refresh_action_view_actual, null);
                RefrItem.setActionView(iconView);
                RefrItem.setIcon(R.drawable.ic_refresh_24dp_actual);
                break;
            }
            case outdated: {
                if (RefrItem.getActionView() != null) RefrItem.getActionView().clearAnimation();
                RefrItem.setActionView(null);
                RefrItem.setIcon(null);
                break;
            }
            default: {

            }
        }
    }

    private void populateAdapter() {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "name"});
        if (searchResult != null && searchResult.length > 0) {
            for (int i = 0; i < searchResult.length; i++) {
                c.addRow(new Object[]{i, searchResult[i]});
            }
        }
        mAdapter.changeCursor(c);
    }                                                   // suggestion cursor adapter


    private void readSharedPreferences() {
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (mSharedPreferences.contains(LAST_PROFILE_NAME)) {
            profileName = mSharedPreferences.getString(LAST_PROFILE_NAME, null);
        }
        if (mSharedPreferences.contains(PROFILE_NAMES_LIST)) {
            profileNamesList = new HashSet<>();
            profileNamesList = mSharedPreferences.getStringSet(PROFILE_NAMES_LIST, new HashSet<>());
            searchResult = profileNamesList.toArray(new String[profileNamesList.size()]);
        }
        Log.i("QstatsMain", "Prefs: PROFILE_NAMES_LIST: " + profileNamesList + " LAST_PROFILE_NAME: " + profileName);
        mAsyncTaskParseFromCache = new AsyncTaskParseFromCache();
        mAsyncTaskParseFromCache.execute();
    }

    private void configureHeader() {
        if (!mViewModel.emptyDb) {
            ImageView namePlateH = findViewById(R.id.namelpate);
            ImageView profileIconH = findViewById(R.id.profile_icon);
            ImageView rangeIconH = findViewById(R.id.range_icon);
            TextView profileNameH = findViewById(R.id.profile_name);
            TextView duelElo = findViewById(R.id.duel_elo);


            profileName = mViewModel.getPlayerStats().getName();
            profileNameH.setText(profileName);
            int elo = mViewModel.getPlayerStats().getPlayerRatings().getDuelRating();
            duelElo.setText(String.valueOf(elo));
            rangeIconH.setImageDrawable(dta.getRangeImageTranslator(elo));
            profileIconH.setImageDrawable(dta.getIconsImageTranslator(mViewModel.getPlayerStats().getPlayerLoadOut().getIconId()));
            namePlateH.setImageDrawable(dta.getNameplatesImageTranslator(mViewModel.getPlayerStats().getPlayerLoadOut().getNamePlateId()));
        }
    }

    protected void configureTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }       //TODO посмотреть разные методы, с иконкой намутить

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

    @Override
    protected void onDestroy() {
        setRefreshIcon(RefreshMode.outdated);
        super.onDestroy();
    }
//__________________________________________________________________________________________________

    static class AsyncTaskGlobal extends AsyncTask<Void, Void, Void> {

        private MyViewModel mViewModel;
        MainActivity a;

        protected void link() {
            a = instanceMainActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            a.setRefreshIcon(MainActivity.RefreshMode.update);
            mViewModel = a.mViewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Networking background = new Networking();

            String dg = background.fetchJSONFromURL(instanceMainActivity.getString(R.string.url_global));
            String tdm = background.fetchJSONFromURL(instanceMainActivity.getString(R.string.url_tdm_leads));
            String duel = background.fetchJSONFromURL(instanceMainActivity.getString(R.string.url_duel_leads));

            mViewModel.parseDataGlobal(dg);
            mViewModel.parseLeaderBoard(tdm, false);
            mViewModel.parseLeaderBoard(duel, true);

            SharedPreferences.Editor editor = a.mSharedPreferences.edit();
            editor.putString("DataGlobal", dg);
            editor.putString("TdmLeads", tdm);
            editor.putString("DuelLeads", duel);

            if (searchName != null) {
                String n = searchName;
                if (searchName.contains("/")) n = searchName.replace("&", "%2F");
                if (searchName.contains("?")) n = searchName.replace("&", "%3F");
                if (searchName.contains("=")) n = searchName.replace("&", "%3D");
                if (searchName.contains("&")) n = searchName.replace("&", "%26");
                if (searchName.contains("#")) n = searchName.replace("&", "%2523");
                Log.i("qStatsGlobalAsynctask", "SEARCHNAME: " + n);

                String summary = background.fetchJSONFromURL(instanceMainActivity.getString(R.string.url_player_summary) + "?name=" + n);
                String stats = background.fetchJSONFromURL(instanceMainActivity.getString(R.string.url_player_stats) + "?name=" + n);
                Log.i("qStatsGlobalAsynctask", "STATS: " + stats);                                                       //=====

                if (stats != null) {
                    if (stats.length() > 70) {
                        mViewModel.parsePlayerSummary(summary);
                        mViewModel.parsePlayerStats(stats);

                        profileName = searchName;
                        editor.putString(LAST_PROFILE_NAME, profileName);
                        profileNamesList.add(profileName);
                        editor.putStringSet(PROFILE_NAMES_LIST, profileNamesList);

                        mViewModel.emptyDb = false;

                        editor.putString("PlayerSummary", summary);
                        editor.putString("PlayerStats", stats);

                        Log.i("qStatsGlobalAsynctask", " NEW NAME: " + profileName);
                    }
                }
            }
            editor.apply();

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            a.vpa.notifyDataSetChanged();
            a.configureHeader();
            a.setRefreshIcon(RefreshMode.outdated);
            a.setRefreshIcon(RefreshMode.actual);
            a.mAsyncTaskGlobal = null;
        }
    }

    static class AsyncTaskNameSearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Networking background = new Networking();
            String nameSearchJson = background.fetchJSONFromURL(instanceMainActivity.getString(R.string.url_player_search) + "?term=" + searchName);
            if (nameSearchJson != null) {
                String[] r = instanceMainActivity.mViewModel.parseSearchResult(nameSearchJson);
                if (r != null) {
                    if (r.length > 0) instanceMainActivity.searchResult = r;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            instanceMainActivity.populateAdapter();
        }
    }

    static class AsyncTaskParseFromCache extends AsyncTask<Void, Void, Void> {

        SharedPreferences mSharedPreferences = instanceMainActivity.mSharedPreferences;
        MyViewModel mViewModel = instanceMainActivity.mViewModel;

        @Override
        protected Void doInBackground(Void... voids) {
            if (mSharedPreferences.contains("DataGlobal")) {
                mViewModel.parseDataGlobal(mSharedPreferences.getString("DataGlobal", null));
                mViewModel.parseLeaderBoard(mSharedPreferences.getString("TdmLeads", null), false);
                mViewModel.parseLeaderBoard(mSharedPreferences.getString("DuelLeads", null), true);
                if (mSharedPreferences.getString("PlayerSummary", null) != null) {
                    mViewModel.parsePlayerSummary(mSharedPreferences.getString("PlayerSummary", null));
                    mViewModel.parsePlayerStats(mSharedPreferences.getString("PlayerStats", null));
                    mViewModel.emptyDb = false;
                }
                Log.i("qStatsParseFromCache", "Prefs: READ DATA : ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            instanceMainActivity.vpa.notifyDataSetChanged();
            instanceMainActivity.configureHeader();
        }
    }
}


