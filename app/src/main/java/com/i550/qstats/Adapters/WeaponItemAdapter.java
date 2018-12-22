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
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.DamageStatusList;
import com.i550.qstats.R;

import java.util.List;


public class WeaponItemAdapter extends ArrayAdapter<DamageStatusList> {
    private Context context;
    private DataTranslator dta;
    private List<DamageStatusList> weapons;


    public WeaponItemAdapter(Context context, int resource, List<DamageStatusList> weapons) {
        super(context, resource, weapons);
        this.context = context;
        this.weapons = weapons;
        dta = DataTranslator.getInstance(context);

    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.weapon_item_view_holder, null);

        ImageView weaponImage = view.findViewById(R.id.weapon_image);
        TextView accuracy = view.findViewById(R.id.accuracy);
        TextView killHits = view.findViewById(R.id.kill_hits);
        TextView killPart = view.findViewById(R.id.kill_part);
        TextView kills = view.findViewById(R.id.kills);
        TextView damage = view.findViewById(R.id.damage);

        DamageStatusList w = weapons.get(position);

        int overallKills = 0;
        for (DamageStatusList i : weapons) overallKills += i.getKills();

        int iHits = w.getHits();
        int iShots = w.getShots();
        int iAcc = (int)Math.round( (double)iHits * 100 / iShots) ;
        int iKills = w.getKills();
        int iKillHits = (int)Math.round((double)iKills / iHits * 100);
        int iKillPart = (int)Math.round((double) iKills / overallKills * 100);

        accuracy.setText(iAcc + "%");
        killHits.setText(iKillHits + "%");
        killPart.setText(iKillPart + "%");
        kills.setText(NumberFormatter.formatNum(iKills));
        damage.setText(NumberFormatter.formatNum(w.getDamage()));
        weaponImage.setImageDrawable(dta.getWeaponsImageTranslationIterable(position));

        return view;
    }
}
