package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.i550.qstats.Adapters.AssetDataTranslator;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.NameSearchEntity;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity implements OnSelectNameFromLeaderList {

    private static final String TAG = "qStats";
    public static final String PREFS = "prefs";
    public static final String PROFILE_NAMES_LIST = "name_list";
    public static final String LAST_PROFILE_NAME = "last_profile_name";
    private static String profileName;
    private static String searchName;
    private static Set<String> profileNamesList = new HashSet<>();

    private SimpleCursorAdapter mSearchNameAdapter;
    private SharedPreferences mSharedPreferences;
    private AssetDataTranslator mAssetDataTranslator;

    @BindView(R.id.tab_layout)
    private TabLayout mTabLayout;
    @BindView(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar_layout)
    private Toolbar mToolbar;

    private ViewPagerAdapter vpa;
    private SearchView searchView;


    MyViewModel mViewModel;
    ServerApi retrofitService;

    MenuItem refreshItem;

    private int[] tabIcons = {
            R.drawable.ic_champions,
            R.drawable.ic_modes,
            R.drawable.ic_medals,
            R.drawable.ic_weapons,
            R.drawable.ic_matches};


    public enum RefreshMode {update, actual, outdated}


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
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://stats.quake.com/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitService = retrofit.create(ServerApi.class);




        mAssetDataTranslator = AssetDataTranslator.getInstance(this);

        ViewPager viewPager = findViewById(R.id.viewpager);

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        createQueryAdapter();

        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);
        viewPager.setAdapter(vpa);

        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        readSharedPreferences();

        mTabLayout.setupWithViewPager(viewPager, false);          //false для того чтобы иконки не исчезали
        configureTabLayout();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                              // inflate mToolbar & searchView

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        refreshItem = menu.findItem(R.id.menu_refresh);
        final View np = findViewById(R.id.header_nameplate);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setSuggestionsAdapter(mSearchNameAdapter);         // on first launch searchview is open

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {

                Cursor c = mSearchNameAdapter.getCursor();
                c.moveToPosition(position);
                String name = c.getString(c.getColumnIndex("name"));
                searchView.setQuery(name, true);
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
                        nameSearch();
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

    @Override
    public void refreshData(String name) {
        if (name != null) searchName = name;
        else searchName = profileName;
        if (checkInternet()) {
            UpdateData();
        }
    }

    private void UpdateData() {

        setRefreshIcon(RefreshMode.update);

        Call<DataGlobal> dg = retrofitService.getGlobalData();
        dg.enqueue(new Callback<DataGlobal>() {
            @Override
            public void onResponse(Call<DataGlobal> call, Response<DataGlobal> response) {
                if (response.isSuccessful()) {
                    mViewModel.setDataGlobal(response.body());
                    Log.d(TAG, "onResponse response.body(): " + response.body());
                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataGlobal> call, Throwable t) {
                Log.e(TAG, "onFailure: " + call);
            }
        });

        Call<LeaderBoard> duelData = retrofitService.getDuelData();
        duelData.enqueue(new Callback<LeaderBoard>() {
            @Override
            public void onResponse(Call<LeaderBoard> call, Response<LeaderBoard> response) {
                if (response.isSuccessful()) mViewModel.setDuelLeads(response.body());
            }

            @Override
            public void onFailure(Call<LeaderBoard> call, Throwable t) {

            }
        });

        Call<LeaderBoard> tdmData = retrofitService.getTdmData();
        tdmData.enqueue(new Callback<LeaderBoard>() {
            @Override
            public void onResponse(Call<LeaderBoard> call, Response<LeaderBoard> response) {
                if (response.isSuccessful()) mViewModel.setTDMLeads(response.body());
            }

            @Override
            public void onFailure(Call<LeaderBoard> call, Throwable t) {

            }
        });

        Call<PlayerSummary> gamesSummary = retrofitService.getGamesSummary(searchName);
        gamesSummary.enqueue(new Callback<PlayerSummary>() {
            @Override
            public void onResponse(Call<PlayerSummary> call, Response<PlayerSummary> response) {
                if (response.isSuccessful()) mViewModel.setPlayerSummary(response.body());

            }

            @Override
            public void onFailure(Call<PlayerSummary> call, Throwable t) {

            }
        });

        Call<PlayerStats> stats = retrofitService.getStats(searchName);
        stats.enqueue(new Callback<PlayerStats>() {
            @Override
            public void onResponse(Call<PlayerStats> call, Response<PlayerStats> response) {
                if (response.isSuccessful())
                    mViewModel.setPlayerStats(response.body());
                profileName = searchName;
                mViewModel.emptyDb = false;                              // временно
                vpa.notifyDataSetChanged();                              // заменится на
                configureHeader();                                       // RXJAVA zip observable
                setRefreshIcon(RefreshMode.outdated);                    //
                setRefreshIcon(RefreshMode.actual);                      //
            }

            @Override
            public void onFailure(Call<PlayerStats> call, Throwable t) {

            }
        });
    }

    public void setRefreshIcon(RefreshMode refreshMode) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotation);
        FrameLayout iconView;
        MenuItem RefrItem = mToolbar.getMenu().findItem(R.id.menu_refresh);
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

    private void populateSuggestionAdapter(String[] searchResult) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "name"});
        if (searchResult != null && searchResult.length > 0) {
            for (int i = 0; i < searchResult.length; i++) {
                c.addRow(new Object[]{i, searchResult[i]});
            }
        }
        mSearchNameAdapter.changeCursor(c);
    }                                                   // suggestion cursor adapter


    private void readSharedPreferences() {
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (mSharedPreferences.contains(LAST_PROFILE_NAME)) {
            profileName = mSharedPreferences.getString(LAST_PROFILE_NAME, null);
        }
        if (mSharedPreferences.contains(PROFILE_NAMES_LIST)) {
            profileNamesList = new HashSet<>();
            profileNamesList = mSharedPreferences.getStringSet(PROFILE_NAMES_LIST, new HashSet<>());
        }
        Log.i("QstatsMain", "Prefs: PROFILE_NAMES_LIST: " + profileNamesList + " LAST_PROFILE_NAME: " + profileName);

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

        vpa.notifyDataSetChanged();
        configureHeader();
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
            rangeIconH.setImageDrawable(mAssetDataTranslator.getRangeImageTranslator(elo));
            profileIconH.setImageDrawable(mAssetDataTranslator.getIconsImageTranslator(mViewModel.getPlayerStats().getPlayerLoadOut().getIconId()));
            namePlateH.setImageDrawable(mAssetDataTranslator.getNameplatesImageTranslator(mViewModel.getPlayerStats().getPlayerLoadOut().getNamePlateId()));
        }
    }

    protected void configureTabLayout() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(tabIcons[i]);
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
        mSearchNameAdapter = new
                SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        Log.i("QstatsMain", "ADAPTER " + mSearchNameAdapter);
    }

    @Override
    protected void onDestroy() {
        setRefreshIcon(RefreshMode.outdated);
        super.onDestroy();
    }

    private void nameSearch() {

        Call<ArrayList<NameSearchEntity>> executeSearch = retrofitService.executeSearch(searchName);
        Log.i(TAG, "searchName " + searchName);
        ArrayList<String> res = new ArrayList<>();

        executeSearch.enqueue(new Callback<ArrayList<NameSearchEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<NameSearchEntity>> call, Response<ArrayList<NameSearchEntity>> response) {
                if (response.isSuccessful()) {
                    for (NameSearchEntity nse : response.body()) {
                        res.add(nse.getEntityName());
                    }
                    String[] s = res.toArray(new String[res.size()]);
                    populateSuggestionAdapter(s);
                    Log.i(TAG, "res q " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NameSearchEntity>> call, Throwable t) {
                Log.i(TAG, "wtf " + t);
            }
        });
    }


}



