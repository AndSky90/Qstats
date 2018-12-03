package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//__________________________________________________________________________________________________

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "qStats";
    private static String profileName = "XF8ShaggyStoned";
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

    void configureTabLayout(){
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            // tabLayout. TODO посмотреть разные методы, с иконкой намутить
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(vpa);
        // viewPager.updateViewLayout();
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        configureTabLayout();

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
    }*/

    public void updateGlobalDataFromServer() {
        new AsyncTaskGlobal().execute();
    }
    /*
TODO: public void getStartFragment(){
        fm.beginTransaction();
        fm.replace()
        fragmentTransaction.addToBackStack(BACKSTACK_TAG)
        // getLayoutInflater().inflate(R.layout.f_champions,null);
*/

//__________________________________________________________________________________________________

     class AsyncTaskGlobal extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            NetQstatsWork background = new NetQstatsWork();

            background.fetchDataGlobal(getString(R.string.url_global));
            background.fetchLeaderBoard(getString(R.string.url_tdm_leads), false);
            background.fetchLeaderBoard(getString(R.string.url_duel_leads), true);
            background.fetchPlayerStats(getString(R.string.url_player_stats), profileName);
            background.fetchPlayerSummary(getString(R.string.url_player_summary), profileName);

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
