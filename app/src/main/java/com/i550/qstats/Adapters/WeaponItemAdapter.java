package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.Champions;
import com.i550.qstats.R;

import java.util.List;

public class WeaponItemAdapter extends ArrayAdapter<Champions> {
    private Context context;
    private DataTranslationAdapters dta;
    private List<Champions> champions;

    public WeaponItemAdapter(Context context, int resource, List<Champions> champions) {
        super(context, resource, champions);
        this.context = context;
        this.champions = champions;
        dta = DataTranslationAdapters.getInstance(context);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        // MatchDetails m = matchDetails.get(position);
        Champions champion = champions.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.champions_selector_view_holder, null);

        ImageView championImage = view.findViewById(R.id.champion_imageview);

       // championImage.setImageDrawable(dta.getChampionsImageTranslationIterable(champion.get()));


        //int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
        //image.setImageResource(imageID);

        return view;
    }

}
