package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.DamageStatusList;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.R;

import java.util.ArrayList;
import java.util.List;


public class ModesItemAdapter extends ArrayAdapter<GameModes> {
    private Context context;
    private DataTranslator dta;
    private List<Champions> champions;
    private int numberChampion;
    private List<GameModes> list;
    private Champions person;

    public ModesItemAdapter(Context context, int resource, List<GameModes> list, int numberChampion, List<Champions> champions) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
        this.champions = champions;
        this.numberChampion = numberChampion;
        dta = DataTranslator.getInstance(context);
        person = champions.get(numberChampion);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        GameModes w;
        List<GameModes> gameModesList = person.getGameModesValues();
        w = gameModesList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.modes_item_view_holder, null);


        TextView matchCompleteCount = view.findViewById(R.id.match_complete_count);
        TextView winLoseRatio = view.findViewById(R.id.win_lose_ratio);
        TextView winsCount = view.findViewById(R.id.wins_count);
        TextView losesCount = view.findViewById(R.id.losses_count);
        TextView majorPickups = view.findViewById(R.id.major_pickups);
        TextView powerPickups = view.findViewById(R.id.power_pickups);
        TextView timePlayedCount = view.findViewById(R.id.time_played_count);
        TextView kdRatio = view.findViewById(R.id.kdr);
        TextView killsCount = view.findViewById(R.id.kills_count);
        TextView deathsCount = view.findViewById(R.id.deaths_count);
        TextView avgLifetime = view.findViewById(R.id.avg_lifetime);


        double dWinsCount = w.getWon();
        double dLosesCount = w.getLost();
        double dMatchCompleteCount = dWinsCount + dLosesCount;
        double dWinLoseRatio = dWinsCount / dLosesCount;
        int iMajorPickups = w.getTacticalPickups();
        int iPowerPickups = w.getPowerPickups();
        int iTimePlayedCount = w.getTimePlayed();   //or lifetime
        double dKillsCount = w.getKills();
        double dDeathsCount = w.getDeaths();
        double dKdRatio = dKillsCount/dDeathsCount;
        double dAvgLifetime = iTimePlayedCount/dDeathsCount;

        matchCompleteCount.setText(String.valueOf(dWinsCount));
        winLoseRatio.setText(String.valueOf(dLosesCount));
        winsCount.setText(String.valueOf(dMatchCompleteCount));
        losesCount.setText(String.valueOf(dWinLoseRatio));
        majorPickups.setText(String.valueOf(iMajorPickups));
        powerPickups.setText(String.valueOf(iPowerPickups));
        timePlayedCount.setText(String.valueOf(iTimePlayedCount));
        kdRatio.setText(String.valueOf(dKillsCount));
        killsCount.setText(String.valueOf(dDeathsCount));
        deathsCount.setText(String.valueOf(dKdRatio));
        avgLifetime.setText(String.valueOf(dAvgLifetime));

        return view;
    }

}
