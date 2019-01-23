package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.i550.qstats.R;

import java.util.List;

public class ChampionsAdapter extends ArrayAdapter<String> {
    private DataTranslator dta;
    private List<String> names;
    private int selectedChampion;
    private LayoutInflater inflater;
    private int colorOfSelectedItem;

    public ChampionsAdapter(Context context, int resource, List<String> names, int selectedChampion) {
        super(context, resource, names);
        this.names = names;
        this.selectedChampion = selectedChampion;
        dta = DataTranslator.getInstance(context);
        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        colorOfSelectedItem = context.getResources().getColor(R.color.shotGun);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = names.get(position);
        View view = inflater.inflate(R.layout.champions_selector_view_holder, null);

        ImageView championImage = view.findViewById(R.id.champion_imageview);
        championImage.setImageDrawable(dta.getChampionsImageTranslation(name));
        if (position==selectedChampion) championImage.setBackgroundColor(colorOfSelectedItem);
        return view;
    }

}
