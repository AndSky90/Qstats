package com.i550.qstats.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i550.qstats.Adapters.LeadersItemAdapter;
import com.i550.qstats.Adapters.RecyclerDecorator;
import com.i550.qstats.Model.Entry;
import com.i550.qstats.MyViewModel;
import com.i550.qstats.OnSelectNameFromLeaderList;
import com.i550.qstats.R;

import java.util.List;

public class FragmentGlobal extends QStatsFragment {

    private OnSelectNameFromLeaderList onSelectNameFromLeaderList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View result = inflater.inflate(R.layout.f_global, container, false);

        MyViewModel model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);


        if ( !model.emptyDb && model.getDuelLeads().getEntries() != null && model.getTdmLeads().getEntries() != null)
        {
            onSelectNameFromLeaderList = (OnSelectNameFromLeaderList) getActivity();
            RecyclerView listViewDuelLeads = result.findViewById(R.id.list_view_duel_leads);
            List<Entry> duelLeads = model.getDuelLeads().getEntries();
            LeadersItemAdapter aDuel = new LeadersItemAdapter(getContext(), duelLeads);
            aDuel.setOnItemClickListener((String name, View v) ->
                    onSelectNameFromLeaderList.refreshData(name));
            listViewDuelLeads.setLayoutManager(new LinearLayoutManager(getContext()));
            listViewDuelLeads.setHasFixedSize(true);
            listViewDuelLeads.addItemDecoration(new RecyclerDecorator(8));
            listViewDuelLeads.setAdapter(aDuel);

            RecyclerView listViewTdmLeads = result.findViewById(R.id.list_view_tdm_leads);
            List<Entry> tdmLeads = model.getTdmLeads().getEntries();
            LeadersItemAdapter aTdm = new LeadersItemAdapter(getContext(), tdmLeads);
            aTdm.setOnItemClickListener((String name, View v) ->
                    onSelectNameFromLeaderList.refreshData(name));
            listViewTdmLeads.setLayoutManager(new LinearLayoutManager(getContext()));
            listViewTdmLeads.setHasFixedSize(true);
            listViewTdmLeads.addItemDecoration(new RecyclerDecorator(8));
            listViewTdmLeads.setAdapter(aTdm);
        }

        return result;
    }
}
