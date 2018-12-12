package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.i550.qstats.Adapters.ChampionsAdapter;
import com.i550.qstats.Adapters.MatchItemAdapter;
import com.i550.qstats.Adapters.WeaponItemAdapter;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.DamageStatusList;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.PlayerProfileStats;
import com.i550.qstats.Model.PlayerSummary.MatchDetails;
import com.i550.qstats.databinding.FGlobalBinding;
import com.i550.qstats.databinding.FMatchesBinding;
import com.i550.qstats.databinding.FChampionsBinding;
import com.i550.qstats.databinding.FMedalsBinding;
import com.i550.qstats.databinding.FModesBinding;
import com.i550.qstats.databinding.FWeaponsBinding;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QStatsFragment extends Fragment {
    public QStatsFragment() {
    }
    static int NUMBER_SELECTED_CHAMPION = 1;

    private int pageNumber;
    private final List<Integer> mFragmentList = Arrays.asList(R.layout.f_global, R.layout.f_medals, R.layout.f_modes, R.layout.f_weapons, R.layout.f_matches, R.layout.f_champions);

    public static QStatsFragment newStatsFragment(int page) {
        QStatsFragment fragment = new QStatsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(mFragmentList.get(pageNumber), container, false);
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);

        switch (pageNumber) {
            case (0): { //global
                FGlobalBinding binding0 = DataBindingUtil.bind(result);
                binding0.setVm(model);
                break;
            }

            case (1): { //medals
                FMedalsBinding binding1 = DataBindingUtil.bind(result);
                binding1.setVm(model);
                ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                List<String> champions = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, champions);
                listViewChampions.setAdapter(adaptChampions);
                break;
            }

            case (2): { //modes
                FModesBinding binding2 = DataBindingUtil.bind(result);
                binding2.setVm(model);
                ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                List<String> champions = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, champions);
                listViewChampions.setAdapter(adaptChampions);
                break;
            }
            case (3): { //weapons
                FWeaponsBinding binding3 = DataBindingUtil.bind(result);
                binding3.setVm(model);
                ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                ListView listViewWeapons = result.findViewById(R.id.list_view_weapons);

                List<String> championNames = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                ArrayAdapter<String> aChampions = new ChampionsAdapter(getContext(), 0, championNames);
                listViewChampions.setAdapter(aChampions);

                List<Champions> c = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray();
                Champions adasd = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray().get(1);
                List<DamageStatusList> weaponsList = adasd.getWeaponsStats();
                ArrayAdapter<DamageStatusList> aWeapons = new WeaponItemAdapter(getContext(), 0, weaponsList, NUMBER_SELECTED_CHAMPION, c);
                listViewWeapons.setAdapter(aWeapons);

                break;
            }
            case (4): { //matches
                FMatchesBinding binding4 = DataBindingUtil.bind(result);
                binding4.setVm(model);
                ListView listView = result.findViewById(R.id.list_view_matches);
                List<MatchDetails> matchDetails = model.getPlayerSummary().getMatch();
                ArrayAdapter<MatchDetails> adapter = new MatchItemAdapter(getContext(), 0, matchDetails);
                listView.setAdapter(adapter);

                break;
            }
            case 5: { //champions
                FChampionsBinding binding5 = DataBindingUtil.bind(result);
                binding5.setVm(model);
                ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                List<String> champions = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, champions);
                listViewChampions.setAdapter(adaptChampions);

                break;
            }

            //    TestViewModel testModel = ViewModelProviders.of(this).get(TestViewModel.class);
            //    binding.setTvm(testModel);
        }
        return result;
    }
}



        /*
        можно в onResume вписать notifyDataSetChanged(у адаптера)
        какое событие произошло? модель поменялась! надо листенер изменения модели ->
        binding.executePendingBindings()
        binding.notifyPropertyChanged();
        binding.notify();
        может быть что похуй, биндинг узнал но не перерисовал, а надо перерисовать и надо нотифай для адаптера
        ProfileActivityBinding binding = ProfileActivityBinding.inflate(layoutInflater, viewGroup, false);
        */


