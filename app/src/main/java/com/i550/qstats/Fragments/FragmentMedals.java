package com.i550.qstats.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.i550.qstats.Adapters.ChampionsAdapter;
import com.i550.qstats.Adapters.MedalsItemAdapter;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.R;

import java.util.List;
import java.util.Map;

public class FragmentMedals extends QStatsFragment  {

    Champions currentChampion;
    List<String> championNames;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View result = inflater.inflate(R.layout.f_medals, container, false);

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

            RecyclerView gridViewMedals = result.findViewById(R.id.grid_view_medals);
            Map<String, Integer> medalsMap = currentChampion.getGameModesValues().get(0).getScoringEvents();
            RecyclerView.Adapter aMedals = new MedalsItemAdapter(getContext(), medalsMap);
            GridLayoutManager manager = new GridLayoutManager(super.getContext(), 5);
            gridViewMedals.setLayoutManager(manager);
            gridViewMedals.setAdapter(aMedals);
        }


        return result;
    }
}
