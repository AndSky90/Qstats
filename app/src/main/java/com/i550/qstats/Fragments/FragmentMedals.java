package com.i550.qstats.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.i550.qstats.Adapters.ChampionsAdapter;
import com.i550.qstats.Adapters.MedalsItemAdapter;
import com.i550.qstats.MainActivityInterface;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.R;

import java.util.List;
import java.util.Map;

public class FragmentMedals extends QStatsFragment {

    private MainActivityInterface mMainActivityInterface;
    private Champions currentChampion;
    private List<String> championsNames;
    private ArrayAdapter<String> championsAdapter;
    private RecyclerView.Adapter medalsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View result = inflater.inflate(R.layout.f_medals, container, false);
        ListView listViewChampions = result.findViewById(R.id.list_view_champions);
        RecyclerView gridViewMedals = result.findViewById(R.id.grid_view_medals);

        mMainActivityInterface = (MainActivityInterface) getActivity();
        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        LiveData<PlayerStats> livePlayerStats = model.getPlayerStats();
        livePlayerStats.observe(this, new Observer<PlayerStats>() {
            @Override
            public void onChanged(PlayerStats playerStats) {
                currentChampion = playerStats.getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION);
                championsNames = playerStats.getPlayerProfileStats().getChampionsNamesArray();
                championsAdapter.notifyDataSetChanged();
                medalsAdapter.notifyDataSetChanged();
            }
        });


        championsAdapter = new ChampionsAdapter(getContext(), 0, championsNames, NUMBER_SELECTED_CHAMPION);
        if (listViewChampions != null) {
            listViewChampions.setAdapter(championsAdapter);
            listViewChampions.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                NUMBER_SELECTED_CHAMPION = i;
                Log.i("qStatsFragment", "NUMBER_SELECTED_CHAMPION = " + NUMBER_SELECTED_CHAMPION);
                mMainActivityInterface.notifyViewPager();
            });
        }

        Map<String, Integer> medalsMap = currentChampion.getGameModesValues().get(0).getScoringEvents();
        medalsAdapter = new MedalsItemAdapter(getContext(), medalsMap);
        GridLayoutManager manager = new GridLayoutManager(super.getContext(), 5);
        gridViewMedals.setLayoutManager(manager);
        gridViewMedals.setAdapter(medalsAdapter);

        return result;
    }
}
