package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.qstats.*;
import com.i550.qstats.Model.Entry;
import com.i550.qstats.Model.PlayerSummary.MatchDetails;

import java.util.List;


public class LeaderBoardAdapter extends ArrayAdapter<Entry> {
    private Context context;
    private DataTranslator dta;
    private List<Entry> leadsList;

    public LeaderBoardAdapter (Context context, int resource, List<Entry> leadsList) {
        super(context, resource, leadsList);
        this.context = context;
        this.leadsList = leadsList;
        dta = DataTranslator.getInstance(context);
    }

    @NonNull public View getView(int position, View convertView, ViewGroup parent) {

        Entry entry = leadsList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_item_view_holder, null);

        ImageView namePlate = view.findViewById(R.id.namelpate);
        ImageView profileIcon = view.findViewById(R.id.profile_icon);
        ImageView rangeIcon = view.findViewById(R.id.range_icon);
        TextView profileName = view.findViewById(R.id.profile_name);
        TextView duelElo = view.findViewById(R.id.duel_elo);

        profileName.setText(entry.getUserName());

        int elo = entry.getEloRating();
        duelElo.setText(String.valueOf(elo));
        rangeIcon.setImageDrawable(dta.getRangeImageTranslator(elo));
        profileIcon.setImageDrawable(dta.getIconsImageTranslator(entry.getProfileIconId()));
        namePlate.setImageDrawable(dta.getNameplatesImageTranslator(entry.getNamePlateId()));

        return view;
    }
}