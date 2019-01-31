package com.i550.qstats.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i550.qstats.Adapters.LeadersItemAdapter;
import com.i550.qstats.Adapters.RecyclerDecorator;
import com.i550.qstats.Model.Entry;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.MainActivityInterface;
import com.i550.qstats.R;

import java.util.List;

public class FragmentGlobal extends QStatsFragment {

    private MainActivityInterface mMainActivityInterface;
    private List<Entry> duelLeads;
    private List<Entry> tdmLeads;
    private LeadersItemAdapter duelAdapter, tdmAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View result = inflater.inflate(R.layout.f_global, container, false);
        RecyclerView listViewDuelLeads = result.findViewById(R.id.list_view_duel_leads);
        RecyclerView listViewTdmLeads = result.findViewById(R.id.list_view_tdm_leads);

        mMainActivityInterface = (MainActivityInterface) getActivity();
        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        LiveData<LeaderBoard> liveTdmLeaderBoard = model.getTdmLeaderBoard();
        liveTdmLeaderBoard.observe(this, new Observer<LeaderBoard>() {
            @Override
            public void onChanged(LeaderBoard leaderBoard) {
                tdmLeads = leaderBoard.getEntries();
                tdmAdapter.notifyDataSetChanged();
            }
        });


        LiveData<LeaderBoard> liveDuelLeaderBoard = model.getDuelLeaderBoard();
        liveDuelLeaderBoard.observe(this, new Observer<LeaderBoard>() {
            @Override
            public void onChanged(LeaderBoard leaderBoard) {
                duelLeads = leaderBoard.getEntries();
                duelAdapter.notifyDataSetChanged();
            }
        });


        duelAdapter = new LeadersItemAdapter(getContext(), duelLeads);
        duelAdapter.setOnItemClickListener((String name, View v) ->
                mMainActivityInterface.refreshData(name));
        listViewDuelLeads.setLayoutManager(new LinearLayoutManager(getContext()));
        listViewDuelLeads.setHasFixedSize(true);
        listViewDuelLeads.addItemDecoration(new RecyclerDecorator(8));
        listViewDuelLeads.setAdapter(duelAdapter);

        tdmAdapter = new LeadersItemAdapter(getContext(), tdmLeads);
        tdmAdapter.setOnItemClickListener((String name, View v) ->
                mMainActivityInterface.refreshData(name));
        listViewTdmLeads.setLayoutManager(new LinearLayoutManager(getContext()));
        listViewTdmLeads.setHasFixedSize(true);
        listViewTdmLeads.addItemDecoration(new RecyclerDecorator(8));
        listViewTdmLeads.setAdapter(tdmAdapter);

        return result;
    }
}
