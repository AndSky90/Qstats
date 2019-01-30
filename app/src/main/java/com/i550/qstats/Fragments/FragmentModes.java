package com.i550.qstats.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.i550.qstats.Adapters.ChampionsAdapter;
import com.i550.qstats.Adapters.ModesItemAdapter;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentModes extends QStatsFragment  {

    Champions currentChampion;
    List<String> championNames;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View result = inflater.inflate(R.layout.f_modes, container, false);

        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        if (!model.emptyDb) {

            currentChampion = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION);
            championNames = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();

            ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, championNames, NUMBER_SELECTED_CHAMPION);
            ListView listViewChampions = result.findViewById(R.id.list_view_champions);
            if (listViewChampions != null) {
                listViewChampions.setAdapter(adaptChampions);
                listViewChampions.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                    NUMBER_SELECTED_CHAMPION = i;
                    Log.i("qStatsFragment", "NUMBER_SELECTED_CHAMPION = " + NUMBER_SELECTED_CHAMPION);
                    ViewPager vp = getActivity().findViewById(R.id.viewpager);
                    vp.getAdapter().notifyDataSetChanged();
                });
            }

            ListView listViewModes = result.findViewById(R.id.list_view_modes);
            ArrayList<String> gameModesTitles = currentChampion.getGameModesTitles();
            ArrayList<GameModes> gameModesValues = currentChampion.getGameModesValues();
            ArrayAdapter<GameModes> amodes = new ModesItemAdapter(getContext(), 0, gameModesValues, gameModesTitles);
            listViewModes.setAdapter(amodes);
            listViewModes.setHeaderDividersEnabled(true);
        }


        return result;
    }
}
