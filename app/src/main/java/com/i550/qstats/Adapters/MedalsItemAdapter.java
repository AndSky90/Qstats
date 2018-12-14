package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MedalsItemAdapter extends ArrayAdapter<String> {
    private Context context;
    private DataTranslator dta;

    private ArrayList<GameModes> gameModesValues;
    private List<String> scoringEventsTitles;
    HashMap<String,Integer> medalsMap;

    public MedalsItemAdapter(Context context, int resource, List<String> scoringEventsTitles ,ArrayList<GameModes> gameModesValues ) {
        super(context, resource, scoringEventsTitles);
        this.context = context;
        this.scoringEventsTitles=scoringEventsTitles;
        this.gameModesValues=gameModesValues;

        dta = DataTranslator.getInstance(context);

        Map<String,Integer> se = gameModesValues.get(1).getScoringEvents();         // берем любой набор scoringEvents с медалями
         medalsMap = new HashMap<>();                               // создаем итоговый набор с медалями из всех режимов игры
        for (String title : se.keySet()) medalsMap.put(title, 0);                           // заполняем его нулями

        for (GameModes mode : gameModesValues)                                             //берем данные всех режимов игры нашего чемпиона
        {
            Map<String, Integer> someScoringEvent = mode.getScoringEvents();                             //для каждого режима игры берем набор медалей
            for (String key : someScoringEvent.keySet()) {                                              // суммируем счетчик каждой медали в наш итоговый набор
                medalsMap.put(key, medalsMap.get(key) + someScoringEvent.get(key) );
            }
        }
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.medals_item_view_holder, null);

        ImageView medalImage = view.findViewById(R.id.medal_image);
        TextView medalCount = view.findViewById(R.id.medal_count);
        TextView medalTitle = view.findViewById(R.id.medal_title);




       /* for (String key : medalsMap.keySet()){                                      //удаляем медали с нулевым счетчиком
            if (medalsMap.get(key)==0) medalsMap.remove(key);
        }*/


        ArrayList<Integer> countM = new ArrayList<>(medalsMap.values());

       // if (countM.get(position) == 0) return null;
        String text = scoringEventsTitles.get(position).replace("SCORING_EVENT_","");
        text = text.replace("_"," ");
        medalCount.setText(String.valueOf(countM.get(position)));
        medalTitle.setText(text);
        medalImage.setImageDrawable(dta.getMedalsImageTranslator(text.replace(" ","").toLowerCase()));
        return view;
    }

}
