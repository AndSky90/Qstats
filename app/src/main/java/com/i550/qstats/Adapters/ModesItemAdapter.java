package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ModesItemAdapter extends ArrayAdapter<GameModes> {
    private Context context;
    private List<String> gameModesTitles;
    private List<GameModes> gameModesValues;
    private DataTranslator dta;

    public ModesItemAdapter(Context context, int resource, List<GameModes> gameModesValues, List<String> gameModesTitles) {
        super(context, resource, gameModesValues);
        this.context = context;
        this.gameModesTitles = gameModesTitles;
        this.gameModesValues = gameModesValues;
        dta = DataTranslator.getInstance(context);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.modes_item_view_holder, null);

        TextView title = view.findViewById(R.id.mode_title);
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

        GameModes w = gameModesValues.get(position);

        title.setText(dta.getGameModeTitleTranslation(gameModesTitles.get(position)));

        int iWinsCount = w.getWon();
        int iLosesCount = w.getLost();
        int iMajorPickups = w.getTacticalPickups();
        int iPowerPickups = w.getPowerPickups();
        long iTimePlayedCount = w.getTimePlayed();   //milliseconds
        int iKillsCount = w.getKills();
        int iDeathsCount = w.getDeaths();

        int iMatchCompleteCount = iWinsCount + iLosesCount + w.getTie();
        double dWinLoseRatio = (double) iWinsCount / iLosesCount;
        double dKdRatio = (double) iKillsCount / iDeathsCount;
        if (iDeathsCount==0) iDeathsCount=1;                                        //в конце раунда смерть anyway
        int iAvgLifetime = Math.round ( iTimePlayedCount / iDeathsCount / 1000 );

        //Date d = new Date(iTimePlayedCount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm:ss ");

        matchCompleteCount.setText(String.valueOf(iMatchCompleteCount));
        winLoseRatio.setText(String.format("%.3f", dWinLoseRatio));
        winsCount.setText(String.valueOf(iWinsCount));
        losesCount.setText(String.valueOf(iLosesCount));
        majorPickups.setText(String.valueOf(iMajorPickups));
        powerPickups.setText(String.valueOf(iPowerPickups));
        timePlayedCount.setText(dateFormat.format(iTimePlayedCount));
        kdRatio.setText(String.format("%.2f", dKdRatio));
        killsCount.setText(String.valueOf(iKillsCount));
        deathsCount.setText(String.valueOf(iDeathsCount));
        avgLifetime.setText(iAvgLifetime + " s");

        return view;
    }

}
