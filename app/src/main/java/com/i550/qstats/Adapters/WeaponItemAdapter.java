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
import com.i550.qstats.R;

import java.util.List;


public class WeaponItemAdapter extends ArrayAdapter<DamageStatusList> {
    private Context context;
    private DataTranslator dta;
    private List<Champions> champions;
    private int numberChampion;
    private List<DamageStatusList> weapons;
    private Champions person;

    public WeaponItemAdapter(Context context, int resource, List<DamageStatusList> weapons, int numberChampion, List<Champions> champions) {
        super(context, resource, weapons);
        this.context = context;
        this.weapons = weapons;
        this.champions = champions;
        this.numberChampion = numberChampion;
        dta = DataTranslator.getInstance(context);
        person = champions.get(numberChampion);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        DamageStatusList w;
        List<DamageStatusList> damageStatusList = person.getWeaponsStats();
        w = damageStatusList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.weapon_item_view_holder, null);

        ImageView weaponImage = view.findViewById(R.id.weapon_image);
        TextView accuracy = view.findViewById(R.id.accuracy);
        TextView killHits = view.findViewById(R.id.kill_hits);
        TextView killPart = view.findViewById(R.id.kill_part);
        TextView kills = view.findViewById(R.id.kills);
        TextView damage = view.findViewById(R.id.damage);

        int overallKills = 0;
        for (DamageStatusList i : damageStatusList) overallKills += i.getKills();
        double dHits = w.getHits();
        double dShots = w.getShots();
        double dAcc = dHits / dShots * 100;
        int iKills = w.getKills();

        double dKillHits = (double) iKills / dHits * 100;
        double dKillPart = (double) iKills / overallKills * 100;

        accuracy.setText((int) dAcc + "%");
        killHits.setText((int) dKillHits + "%");
        killPart.setText((int) dKillPart + "%");
        kills.setText(String.valueOf(iKills));
        damage.setText(String.valueOf(w.getDamage()));
        weaponImage.setImageDrawable(dta.getWeaponsImageTranslationIterable(position));

        Log.i("qStatsWeaponAdapter", " Input search: " + dAcc + " " + dKillHits + " " + dKillPart);
        //int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
        //image.setImageResource(imageID);

        return view;
    }

}
