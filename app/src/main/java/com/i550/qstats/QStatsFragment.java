package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.DamageStatusList;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.Model.PlayerSummary.MatchDetails;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QStatsFragment extends Fragment {
    public QStatsFragment() {
    }

    static int NUMBER_SELECTED_CHAMPION = 0;

    private int pageNumber;
    private final List<Integer> mFragmentList = Arrays.asList(R.layout.f_global, R.layout.f_medals, R.layout.f_modes, R.layout.f_weapons, R.layout.f_matches);

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(mFragmentList.get(pageNumber), container, false);
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);

        if (!model.emptyDb) {        //  database empty checking

            Champions currentChampion = model.getPlayerStats().getPlayerProfileStats().getChampionsValuesArray().get(NUMBER_SELECTED_CHAMPION);
            List<String> championNames = model.getPlayerStats().getPlayerProfileStats().getChampionsNamesArray();
            ArrayAdapter<String> adaptChampions = new ChampionsAdapter(getContext(), 0, championNames, NUMBER_SELECTED_CHAMPION);
            ListView listViewChampions = result.findViewById(R.id.list_view_champions);
            if (listViewChampions != null) {
                listViewChampions.setAdapter(adaptChampions);
                listViewChampions.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                    NUMBER_SELECTED_CHAMPION = i;
                    Log.i("qStats", "NUMBER_SELECTED_CHAMPION = " + NUMBER_SELECTED_CHAMPION);
                    ViewPager vp = getActivity().findViewById(R.id.viewpager);
                    vp.getAdapter().notifyDataSetChanged();
                });
            }

            switch (pageNumber) {
                case (0): { //global
                    RecyclerView recyclerView = result.findViewById(R.id.weapons_recycler_view);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(layoutManager);

                    //   weaponsPercentage = model.getDataGlobal().getTotalDeaths();
                    //  MyAdapter adapter = new MyAdapter(myDataset);
                    // recyclerView.setAdapter(adapter);

                    ListView listViewDuelLeads = result.findViewById(R.id.list_view_duel_leads);                //duel recycleview
                    List<Entry> duelLeads = model.getDuelLeads().getEntries();
                    ArrayAdapter<Entry> aDuel = new LeaderBoardAdapter(getContext(), 0, duelLeads);
                    listViewDuelLeads.setAdapter(aDuel);

                    ListView listViewTdmLeads = result.findViewById(R.id.list_view_tdm_leads);                   //tdm recycleview
                    List<Entry> tdmLeads = model.getTdmLeads().getEntries();
                    ArrayAdapter<Entry> aTdm = new LeaderBoardAdapter(getContext(), 0, tdmLeads);
                    listViewTdmLeads.setAdapter(aTdm);

                    break;
                }

                case (1): { //medals

                    RecyclerView gridViewMedals = result.findViewById(R.id.grid_view_medals);
                    ArrayList<GameModes> gameModesValues = currentChampion.getGameModesValues();
                    ArrayList<String> scoringEventsTitles = new ArrayList<>(gameModesValues.get(1).getScoringEvents().keySet());
                    ArrayAdapter<String> aMedals = new MedalsItemAdapter(getContext(), 0, scoringEventsTitles, gameModesValues);
                    gridViewMedals.setAdapter(aMedals);


                    break;
                }

                case (2): { //modes+


                    ListView listViewModes = result.findViewById(R.id.list_view_modes);
                    ArrayList<String> gameModesTitles = currentChampion.getGameModesTitles();
                    ArrayList<GameModes> gameModesValues = currentChampion.getGameModesValues();
                    ArrayAdapter<GameModes> amodes = new ModesItemAdapter(getContext(), 0, gameModesValues, gameModesTitles);
                    listViewModes.setAdapter(amodes);
                    break;
                }
                case (3): { //weapons+


                    ListView listViewWeapons = result.findViewById(R.id.list_view_weapons);
                    List<DamageStatusList> weaponsList = currentChampion.getWeaponsStats();
                    ArrayAdapter<DamageStatusList> aWeapons = new WeaponItemAdapter(getContext(), 0, weaponsList);
                    listViewWeapons.setAdapter(aWeapons);
                    break;
                }
                case (4): { //matches+
                    ListView listView = result.findViewById(R.id.list_view_matches);
                    List<MatchDetails> matchDetails = model.getPlayerSummary().getMatch();
                    ArrayAdapter<MatchDetails> adapter = new MatchItemAdapter(getContext(), 0, matchDetails);
                    listView.setAdapter(adapter);
                    break;
                }


            }
            return result;
        } else return null;
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


