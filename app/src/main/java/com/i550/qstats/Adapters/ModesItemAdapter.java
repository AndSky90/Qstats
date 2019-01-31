package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class ModesItemAdapter extends ArrayAdapter<GameModes> {         //можно ли это заменить ViewHolder-ом
    private Context context;
    private List<String> gameModesTitles;
    private List<GameModes> gameModesValues;
    private AssetDataTranslator dta;

    public ModesItemAdapter(Context context, int resource, List<GameModes> gameModesValues, List<String> gameModesTitles) {
        super(context, resource, gameModesValues);
        this.context = context;
        this.gameModesTitles = gameModesTitles;
        this.gameModesValues = gameModesValues;
        dta = AssetDataTranslator.getInstance(context);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        GameModes w = gameModesValues.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        long iTimePlayedCount = w.getTimePlayed();   //milliseconds

        if (iTimePlayedCount == 0) {                                                        //если в режим не играли, возвращаем заглушку с текстом
            View view = inflater.inflate(R.layout.modes_no_games_played, null);
            TextView title = view.findViewById(R.id.mode_title);
            title.setText(dta.getGameModeTitleTranslation(gameModesTitles.get(position)));
            return view;

        } else {

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
            TextView abilityKills = view.findViewById(R.id.abil_kills);

            int iWinsCount = w.getWon();
            int iLosesCount = w.getLost();
            int iKillsCount = w.getKills();
            int iDeathsCount = w.getDeaths();
            int iMatchCompleteCount = iWinsCount + iLosesCount;
            double dWinLoseRatio = (double) iWinsCount / iLosesCount;
            double dKdRatio = (double) iKillsCount / iDeathsCount;
            if (iDeathsCount == 0)
                iDeathsCount = 1;                                        //в конце раунда смерть anyway
            int iAvgLifetime = Math.round(iTimePlayedCount / iDeathsCount / 1000);
            SimpleDateFormat dateFormat;
            if (iTimePlayedCount < 3_600_000L) dateFormat = new SimpleDateFormat("m'm 's's'", Locale.getDefault());
            else if (iTimePlayedCount < 86_400_000L) dateFormat = new SimpleDateFormat("HH'h 'm'm'", Locale.getDefault());
                else if (iTimePlayedCount < 2_592_000_000L)  dateFormat = new SimpleDateFormat("DD'd 'HH'h'", Locale.getDefault());
                     else if (iTimePlayedCount<31_536_000_000L) dateFormat = new SimpleDateFormat("MM'm 'DD'd'", Locale.getDefault());
                         else dateFormat = new SimpleDateFormat("YY y. MM m.", Locale.getDefault());

            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            title.setText(dta.getGameModeTitleTranslation(gameModesTitles.get(position)));
            matchCompleteCount.setText(NumberFormatter.formatNum(iMatchCompleteCount));
            winLoseRatio.setText(String.format("%.3f", dWinLoseRatio));
            winsCount.setText(String.valueOf(iWinsCount));
            losesCount.setText(String.valueOf(iLosesCount));
            majorPickups.setText(String.valueOf(w.getTacticalPickups()));
            powerPickups.setText(String.valueOf(w.getPowerPickups()));
            timePlayedCount.setText(dateFormat.format(iTimePlayedCount));
            kdRatio.setText(String.format("%.2f", dKdRatio));
            killsCount.setText(String.valueOf(iKillsCount));
            deathsCount.setText(String.valueOf(iDeathsCount));
            avgLifetime.setText(iAvgLifetime + " s");
            abilityKills.setText(String.valueOf(w.getScoringEvents().get("SCORING_EVENT_ABILITYKILL")));

            return view;
        }

    }
}