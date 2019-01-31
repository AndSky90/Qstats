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
import com.i550.qstats.Model.PlayerSummary.MatchDetails;

import java.util.List;


public class MatchItemAdapter extends ArrayAdapter<MatchDetails> {
    private Context context;
    private AssetDataTranslator dta;
    private List<MatchDetails> matchDetails;
    private LayoutInflater inflater;

    public MatchItemAdapter(Context context, int resource, List<MatchDetails> matchDetails) {
        super(context, resource, matchDetails);
        this.context = context;
        this.matchDetails = matchDetails;
        dta = AssetDataTranslator.getInstance(context);
        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        MatchDetails m = matchDetails.get(position);

        View view = inflater.inflate(R.layout.matches_item_view_holder, null);

        ImageView mapImage = view.findViewById(R.id.map_image);
        ImageView gamemodeImage = view.findViewById(R.id.gamemode_image);
        TextView gamemodeTitle = view.findViewById(R.id.gamemode_title);
        TextView mapName = view.findViewById(R.id.map_title);
        TextView timeTitle = view.findViewById(R.id.time_elatsed_title);
        TextView winLoseTitle = view.findViewById(R.id.win_lose_title);
        TextView redTitle = view.findViewById(R.id.red_team_title);
        TextView blueTitle = view.findViewById(R.id.blue_team_title);

        gamemodeTitle.setText(dta.getGameModeTitleTranslation(m.getGameMode()));

        mapName.setText(dta.getMapTitleTranslation(m.getMapName()));
        String timePlay = m.getTime().substring(0, 19).replace("T"," @ ");      //formatting play-time string
        timeTitle.setText(timePlay);
        if (m.getWon()) {
            winLoseTitle.setText(R.string.victory);
            winLoseTitle.setTextColor(context.getResources().getColor(R.color.colorVictory));
        } else {
            winLoseTitle.setText(R.string.defeat);
            winLoseTitle.setTextColor(context.getResources().getColor(R.color.colorDefeat));
        }

        if (m.getGameMode().equals("GameModeTeamDeathmatch")) {
            redTitle.setText(m.getScore().get(0));
            blueTitle.setText(m.getScore().get(1));
        } else {
            redTitle.setText(m.getRank() + " place");
            redTitle.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        mapImage.setImageDrawable(dta.getMapImageTranslation(m.getMapName()));
        gamemodeImage.setImageDrawable(dta.getGameModeImageTranslation(m.getGameMode()));

        //int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
        //image.setImageResource(imageID);

        return view;
    }
}