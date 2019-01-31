package com.i550.qstats.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.i550.qstats.Adapters.ChampionsAdapter;
import com.i550.qstats.Adapters.WeaponItemAdapter;
import com.i550.qstats.MainActivityInterface;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.DamageStatusList;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.R;

import java.util.List;

public class FragmentWeapons extends QStatsFragment {

    private MainActivityInterface mMainActivityInterface;
    private List<DamageStatusList> currentChampionWeapons;
    private List<String> championsNames;
    private ArrayAdapter<String> championsAdapter;
    private ArrayAdapter<DamageStatusList> weaponsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View result = inflater.inflate(R.layout.f_weapons, container, false);
        ListView listViewChampions = result.findViewById(R.id.list_view_champions);
        ListView listViewWeapons = result.findViewById(R.id.list_view_weapons);

        mMainActivityInterface = (MainActivityInterface) getActivity();
        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        LiveData<PlayerStats> livePlayerStats = model.getPlayerStats();
        livePlayerStats.observe(this, new Observer<PlayerStats>() {
            @Override
            public void onChanged(PlayerStats playerStats) {
                currentChampionWeapons = playerStats.getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION).getWeaponsStats();
                championsNames = playerStats.getPlayerProfileStats().getChampionsNamesArray();
                championsAdapter.notifyDataSetChanged();
                weaponsAdapter.notifyDataSetChanged();
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

        weaponsAdapter = new WeaponItemAdapter(getContext(), 0, currentChampionWeapons);
        listViewWeapons.setAdapter(weaponsAdapter);

        return result;
    }
}
