package com.i550.qstats.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.i550.qstats.R;

import java.util.ArrayList;
import java.util.Map;

public class UsageAdapter extends BaseAdapter {         //глобальная статистика - пока удалено

    private AssetDataTranslator dta;
    private Map<String, String> totalData;
    private Map<String, String> changeData;
    private ArrayList<String> orderedNames;
    private LayoutInflater inflater;

    public UsageAdapter(Context context, Map<String, String> totalData, Map<String, String> changeData) {
        super();
        this.totalData = totalData;
        this.changeData = changeData;
        dta = AssetDataTranslator.getInstance(context);
        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        orderedNames = new ArrayList<>(totalData.keySet());
    }

    @Override
    public int getCount() {
        return totalData.size();
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return orderedNames.get(i);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.usage_item_view_holder, null);

        String name = orderedNames.get(position);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView percentageTextView = view.findViewById(R.id.percentage);
        TextView changeTextView = view.findViewById(R.id.change);

        imageView.setImageDrawable(dta.getChampionsImageTranslation(name));
        percentageTextView.setText(totalData.get(name));
        changeTextView.setText(changeData.get(name));

        return view;
    }

}
