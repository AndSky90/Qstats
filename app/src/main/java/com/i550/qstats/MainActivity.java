package com.i550.qstats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.BaseColumns;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

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

import com.i550.qstats.Model.NameSearchEntity;
import com.i550.qstats.Model.PlayerStats.PlayerStats;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    public static final String LOG_D_TAG = "qStatsLogs";
    public static final String PREFS = "prefs";
    public static final String CURSOR_PROFILE_NAME = "name";

    private SimpleCursorAdapter mSearchNameAdapter;
    private SharedPreferences mSharedPreferences;
    private AssetDataTranslator mAssetDataTranslator;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar_layout)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.namelpate)
    ImageView namePlateH;
    @BindView(R.id.profile_icon)
    ImageView profileIconH;
    @BindView(R.id.range_icon)
    ImageView rangeIconH;
    @BindView(R.id.profile_name)
    TextView profileNameH;
    @BindView(R.id.duel_elo)
    TextView duelEloH;

    private ViewPagerAdapter mViewPagerAdapter;
    private SearchView mSearchView;

    MyViewModel mViewModel;

    MenuItem refreshMenuItem;

    private int[] tabIcons = {
            R.drawable.ic_champions,
            R.drawable.ic_modes,
            R.drawable.ic_medals,
            R.drawable.ic_weapons,
            R.drawable.ic_matches};


    public enum RefreshState {update, actual, outdated}


/*    @Override
    protected void onSaveInstanceState(Bundle sis) {
        super.onSaveInstanceState(sis);
        sis.putString("profileName", profileName);
    }

    @Override
    protected void onRestoreInstanceState(Bundle sis) {
        super.onRestoreInstanceState(sis);
        profileName = sis.getString("profileName");
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mAssetDataTranslator = AssetDataTranslator.getInstance(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        createQueryAdapter();

        FragmentManager fm = getSupportFragmentManager();
        mViewPagerAdapter = new ViewPagerAdapter(fm);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        readSharedPreferences();

        mTabLayout.setupWithViewPager(mViewPager, false);                                    //false для того чтобы иконки не исчезали
        configureTabLayout();

        LiveData<PlayerStats> livePlayerStats = mViewModel.getPlayerStats();
        livePlayerStats.observe(this, new Observer<PlayerStats>() {
            @Override
            public void onChanged(PlayerStats playerStats) {

                String name = playerStats.getName();
                profileNameH.setText(name);
                int elo = playerStats.getPlayerRatings().getDuelRating();
                duelEloH.setText(String.valueOf(elo));
                rangeIconH.setImageDrawable(mAssetDataTranslator.getRangeImageTranslator(elo));
                profileIconH.setImageDrawable(mAssetDataTranslator.getIconsImageTranslator(playerStats.getPlayerLoadOut().getIconId()));
                namePlateH.setImageDrawable(mAssetDataTranslator.getNameplatesImageTranslator(playerStats.getPlayerLoadOut().getNamePlateId()));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                                     // inflate mToolbar & mSearchView

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        refreshMenuItem = menu.findItem(R.id.menu_refresh);
        final View header = findViewById(R.id.header_nameplate);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setSuggestionsAdapter(mSearchNameAdapter);

        mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor c = mSearchNameAdapter.getCursor();      //TODO getSuggAdapter??
                c.moveToPosition(position);
                String query = c.getString(c.getColumnIndex(CURSOR_PROFILE_NAME));
                mSearchView.setQuery(query, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(query);
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query != null && query.length() > 0) {
                    if (checkInternet()) {
                        nameSearch(query);
                    }
                }
                return true;
            }
        });
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                header.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                header.setVisibility(View.VISIBLE);
                return true;
            }
        });
        // if (profileName==null) menuItem.expandActionView();                                      // activate if intended -> searchview is open on first launch
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

        ObserveRefreshState();

        refreshData(null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_D_TAG, "onOptionsItemSelected: MenuItem ID pressed: " + item.getItemId());
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

    private void populateSuggestionAdapter(String[] searchResult) {                                  // suggestion cursor adapter
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, CURSOR_PROFILE_NAME});
        if (searchResult != null && searchResult.length > 0) {
            for (int i = 0; i < searchResult.length; i++) {
                c.addRow(new Object[]{i, searchResult[i]});
            }
        }
        mSearchNameAdapter.changeCursor(c);
    }

    void ObserveRefreshState() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LiveData<RefreshState> liveRefreshState = mViewModel.getRefreshState();
        liveRefreshState.observe(this, new Observer<RefreshState>() {
            @Override
            public void onChanged(RefreshState state) {

                Log.d(LOG_D_TAG, "Refresh State observe: " + state);
                Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotation);
                FrameLayout iconView;
                MenuItem refreshItem = mToolbar.getMenu().findItem(R.id.menu_refresh);

                switch (state) {
                    case update: {
                        iconView = (FrameLayout) inflater.inflate(R.layout.refresh_action_view, null);
                        iconView.startAnimation(rotation);
                        refreshItem.setActionView(iconView);
                        break;
                    }
                    case actual: {
                        iconView = (FrameLayout) inflater.inflate(R.layout.refresh_action_view_actual, null);
                        refreshItem.setActionView(iconView);
                        refreshItem.setIcon(R.drawable.ic_refresh_24dp_actual);
                        break;
                    }
                    case outdated: {
                        if (refreshItem.getActionView() != null) {
                            refreshItem.getActionView().clearAnimation();
                        }
                        refreshItem.setActionView(null);
                        refreshItem.setIcon(null);
                        break;
                    }
                    default: {
                    }
                }
            }
        });
    }


    private void readSharedPreferences() {
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        if (mSharedPreferences.contains("DataGlobal")) {
            mViewModel.parseDataGlobal(mSharedPreferences.getString("DataGlobal", null));
            mViewModel.parseLeaderBoard(mSharedPreferences.getString("TdmLeads", null), false);
            mViewModel.parseLeaderBoard(mSharedPreferences.getString("DuelLeads", null), true);
            if (mSharedPreferences.getString("PlayerSummary", null) != null) {
                mViewModel.parsePlayerSummary(mSharedPreferences.getString("PlayerSummary", null));
                mViewModel.parsePlayerStats(mSharedPreferences.getString("PlayerStats", null));
            }
            Log.i("qStatsParseFromCache", "Prefs: READ DATA : ");
        }

        mViewPagerAdapter.notifyDataSetChanged();

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
        final String[] from = new String[]{CURSOR_PROFILE_NAME};
        final int[] to = new int[]{android.R.id.text1};
        mSearchNameAdapter = new
                SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        Log.d(LOG_D_TAG, "createQueryAdapter: " + mSearchNameAdapter);
    }

    @Override
    public void notifyViewPager() {
        mViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshData(String name) {
        if (checkInternet()) {
            mViewModel.refreshAllData(name);
        }
    }

    private void nameSearch(String query) {

        Call<ArrayList<NameSearchEntity>> executeSearch = mViewModel.retrofitApi.callPlayerSearch(query);
        Log.d(LOG_D_TAG, "nameSearch Query: " + query);
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
                    Log.d(LOG_D_TAG, "nameSearch Responce:" + response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NameSearchEntity>> call, Throwable t) {
                Log.d(LOG_D_TAG, "nameSearch Failure: " + t);
            }
        });
    }


}



