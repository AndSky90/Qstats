package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;


import com.i550.qstats.Adapters.ChampionsAdapter;
import com.i550.qstats.Adapters.LeaderBoardAdapter;
import com.i550.qstats.Adapters.MatchItemAdapter;
import com.i550.qstats.Adapters.MedalsItemAdapter;
import com.i550.qstats.Adapters.ModesItemAdapter;
import com.i550.qstats.Adapters.WeaponItemAdapter;
import com.i550.qstats.Model.Entry;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.DamageStatusList;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.Model.PlayerSummary.MatchDetails;
import com.i550.qstats.databinding.FGlobalBinding;
import com.i550.qstats.databinding.FMatchesBinding;
import com.i550.qstats.databinding.FChampionsBinding;
import com.i550.qstats.databinding.FMedalsBinding;
import com.i550.qstats.databinding.FModesBinding;
import com.i550.qstats.databinding.FWeaponsBinding;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QStatsFragment extends Fragment {
    public QStatsFragment() {
    }
    static int NUMBER_SELECTED_CHAMPION = 3;

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
        if (!model.getPlayerStats().getPlayerProfileStats().getChampions().isEmpty()) {        //  database empty checking

            switch (pageNumber) {
                case (0): { //global
                    FGlobalBinding binding0 = DataBindingUtil.bind(result);
                    binding0.setVm(model);

                    ListView listViewDuelLeads = result.findViewById(R.id.list_view_duel_leads);
                    ListView listViewTdmLeads = result.findViewById(R.id.list_view_tdm_leads);
                    List<Entry> duelLeads = model.getDuelLeads().getEntries();
                    List<Entry> tdmLeads = model.getTdmLeads().getEntries();
                    ArrayAdapter<Entry> aDuel = new LeaderBoardAdapter(getContext(), 0, duelLeads);
                    ArrayAdapter<Entry> aTdm = new LeaderBoardAdapter(getContext(), 0, tdmLeads);
                    listViewDuelLeads.setAdapter(aDuel);
                    listViewTdmLeads.setAdapter(aTdm);

                    break;
                }

                case (1): { //medals
                    FMedalsBinding binding1 = DataBindingUtil.bind(result);
                    binding1.setVm(model);
                    ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                    List<String> champions = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                    ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, champions);
                    listViewChampions.setAdapter(adaptChampions);


                    GridView gridViewMedals = result.findViewById(R.id.grid_view_medals);
                    Champions info = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION);
                    ArrayList<GameModes> gameModesValues = info.getGameModesValues();
                    ArrayList<String> scoringEventsTitles = new ArrayList<> (gameModesValues.get(1).getScoringEvents().keySet());

                    ArrayAdapter<String> aMedals = new MedalsItemAdapter(getContext(), 0, scoringEventsTitles, gameModesValues);
                    gridViewMedals.setAdapter(aMedals);


                    break;
                }

                case (2): { //modes+
                    FModesBinding binding2 = DataBindingUtil.bind(result);
                    binding2.setVm(model);
                    ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                    List<String> champions = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                    ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, champions);
                    listViewChampions.setAdapter(adaptChampions);


                    ListView listViewModes = result.findViewById(R.id.list_view_modes);
                    Champions info = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION);
                    ArrayList<String> gameModesTitles = info.getGameModesTitles();
                    ArrayList<GameModes> gameModesValues = info.getGameModesValues();

                    ArrayAdapter<GameModes> amodes = new ModesItemAdapter(getContext(), 0, gameModesValues, gameModesTitles);
                    listViewModes.setAdapter(amodes);

                    break;
                }
                case (3): { //weapons+
                    FWeaponsBinding binding3 = DataBindingUtil.bind(result);
                    binding3.setVm(model);
                    ListView listViewChampions = result.findViewById(R.id.list_view_champions);
                    List<String> championNames = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
                    ArrayAdapter<String> aChampions = new ChampionsAdapter(getContext(), 0, championNames);
                    listViewChampions.setAdapter(aChampions);

                    ListView listViewWeapons = result.findViewById(R.id.list_view_weapons);
                    Champions info = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION);
                    List<DamageStatusList> weaponsList = info.getWeaponsStats();
                    ArrayAdapter<DamageStatusList> aWeapons = new WeaponItemAdapter(getContext(), 0, weaponsList);
                    listViewWeapons.setAdapter(aWeapons);

                    break;
                }
                case (4): { //matches+
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


            }
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


