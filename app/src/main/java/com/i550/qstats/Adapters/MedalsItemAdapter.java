package com.i550.qstats.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.qstats.R;

import java.util.ArrayList;
import java.util.Map;


public class MedalsItemAdapter extends RecyclerView.Adapter<MedalsItemAdapter.MedalsViewHolder> {
    private Context context;
    private Map<String, Integer> medalsMap;
    private DataTranslator dta;

    public MedalsItemAdapter(Context context, Map<String, Integer> medalsMap) {
        this.context = context;
        this.medalsMap = medalsMap;
        dta = DataTranslator.getInstance(context);
    }

    static class MedalsViewHolder extends RecyclerView.ViewHolder {
        private ImageView medalImage;
        private TextView medalCount;
        private TextView medalTitle;

        MedalsViewHolder(LayoutInflater i, ViewGroup parent) {
            super(i.inflate(R.layout.medals_item_view_holder, parent, false));

            medalImage = itemView.findViewById(R.id.medal_image);       //itemView - встроенная ссылка на один элемент списка
            medalCount = itemView.findViewById(R.id.medal_count);
            medalTitle = itemView.findViewById(R.id.medal_title);
        }
    }

    @Override
    @NonNull
    public MedalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedalsViewHolder(LayoutInflater.from(context), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MedalsViewHolder holder, int position) {

        Map<String, Drawable> imagesMap = dta.getMedalsImageTranslator();
        ArrayList<String> medalsTitleList = new ArrayList<>(imagesMap.keySet());
        String text = medalsTitleList.get(position);
        holder.medalImage.setImageDrawable(imagesMap.get(text));
        text = text.substring(4);                                                                               //cut number out of filename
        //holder.medalTitle.setText(text.replace("_", " "));                                                    //title of medals disabled (android:text="" add! & 2dp mardin all)
        holder.medalCount.setText(String.valueOf(medalsMap.get("SCORING_EVENT_" + text.toUpperCase())));
    }

    @Override
    public int getItemCount() {
        return dta.getSizeMedalsList();
    }
}







