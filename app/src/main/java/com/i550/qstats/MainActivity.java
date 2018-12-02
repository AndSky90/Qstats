package com.i550.qstats;

import android.arch.lifecycle.ViewModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        vpa = new ViewPagerAdapter(fm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(vpa);
       // viewPager.updateViewLayout();
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
           // tabLayout. TODO посмотреть разные методы, с иконкой намутить
        }

        TestViewModel testModel = ViewModelProviders.of(this).get(TestViewModel.class);
        //  MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        updateGlobalDataFromServer();
        //model.ge

        Test te = new Test();
        te.setJopa("aage2324geg");
        testModel.setTest(te);




        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click header");
                // TODO: getStartFragment();
            }});
    }

    public void updateView(){
        viewPager.invalidate();
    }

    public void updateGlobalDataFromServer() {
        new AsyncTaskGlobal().execute();
    }
    /*
TODO: public void getStartFragment(){
        fm.beginTransaction();
        fm.replace()
        fragmentTransaction.addToBackStack(BACKSTACK_TAG)
        // getLayoutInflater().inflate(R.layout.f_profile_summary,null);
*/
//__________________________________________________________________________________________________

    public class AsyncTaskGlobal extends AsyncTask<Void, Void, DataGlobal> {
        @Override
        protected DataGlobal doInBackground(Void... voids) {

            DataGlobal dg = new NetQstatsWork().fetchItems(getApplication().getString(R.string.url_global), null, null);
            return dg;
            //  new NetQstatsWork().fetchItems(getString(R.string.url_duel_leads), null, null);
            // new NetQstatsWork().fetchItems(getString(R.string.url_tdm_leads), null, null);

            // new NetQstatsWork().fetchItems(getString(R.string.url_player_stats), "name", "XF8ShaggyStoned");
            //  new NetQstatsWork().fetchItems(getString(R.string.url_player_summary), "name", "XF8ShaggyStoned");
        }

        @Override
        protected void onPostExecute(DataGlobal dg) {
         //   setDataGlobal(dg);
            MyViewModel.setDataGlobal(dg);
         vpa.notifyDataSetChanged();
        }
    }

}
    /*  private class SearchAsyncTask extends AsyncTask<String,Void,Void> {

          @Override
          protected Void doInBackground(String... strings) {
              return null;
          }
      }
    private class GameSummaryAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }
    }*/
