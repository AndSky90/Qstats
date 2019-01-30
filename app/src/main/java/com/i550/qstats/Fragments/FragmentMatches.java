package com.i550.qstats.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.i550.qstats.Adapters.MatchItemAdapter;
import com.i550.qstats.Model.PlayerSummary.MatchDetails;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.R;

import java.util.List;

public class FragmentMatches extends QStatsFragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View result = inflater.inflate(R.layout.f_matches, container, false);

        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        if (!model.emptyDb) {
            ListView listView = result.findViewById(R.id.list_view_matches);
            List<MatchDetails> matchDetails = model.getPlayerSummary().getMatch();
            ArrayAdapter<MatchDetails> adapter = new MatchItemAdapter(getContext(), 0, matchDetails);
            listView.setAdapter(adapter);
        }


        return result;
    }
}
