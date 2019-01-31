package com.i550.qstats.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.i550.qstats.Adapters.MatchItemAdapter;
import com.i550.qstats.Model.PlayerStats.PlayerStats;
import com.i550.qstats.Model.PlayerSummary.MatchDetails;
import com.i550.qstats.Model.PlayerSummary.PlayerSummary;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.R;

import java.util.List;

public class FragmentMatches extends QStatsFragment {

    private ArrayAdapter<MatchDetails> matchDetailsAdapter;
    private List<MatchDetails> matchDetails;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View result = inflater.inflate(R.layout.f_matches, container, false);
        ListView listView = result.findViewById(R.id.list_view_matches);

        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        LiveData<PlayerSummary> livePlayerStats = model.getPlayerSummary();
        livePlayerStats.observe(this, new Observer<PlayerSummary>() {
            @Override
            public void onChanged(PlayerSummary playerSummary) {
                matchDetails = playerSummary.getMatch();
                matchDetailsAdapter.notifyDataSetChanged();
            }
        });

            matchDetailsAdapter = new MatchItemAdapter(getContext(), 0, matchDetails);
            listView.setAdapter(matchDetailsAdapter);

        return result;
    }
}
