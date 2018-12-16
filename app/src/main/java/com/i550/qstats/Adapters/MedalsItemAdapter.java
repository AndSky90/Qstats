package com.i550.qstats.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.i550.qstats.Model.PlayerStats.PlayerProfileStats.GameModes;
import com.i550.qstats.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MedalsItemAdapter extends RecyclerView.Adapter<MedalsItemAdapter.MyViewHolder>{
    private Context context;
    private DataTranslator dta;
    private ArrayList<GameModes> gameModesValues;
    private List<String> scoringEventsTitles;
    private Map<String,Integer> medalsMap;

    public MedalsItemAdapter(Context context, List<String> scoringEventsTitles ,ArrayList<GameModes> gameModesValues) {
        this.context = context;
        this.scoringEventsTitles=scoringEventsTitles;
        this.gameModesValues=gameModesValues;
        medalsMap = gameModesValues.get(0).getScoringEvents();
        dta = DataTranslator.getInstance(context);
    }

    //=============================================================================================

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private  ImageView medalImage;
        private TextView medalCount;
        private TextView medalTitle;

         MyViewHolder(LayoutInflater i, ViewGroup parent) {
            super(i.inflate(R.layout.medals_item_view_holder, parent,false));

            medalImage = itemView.findViewById(R.id.medal_image);       //itemView - встроенная байда
            medalCount = itemView.findViewById(R.id.medal_count);
            medalTitle = itemView.findViewById(R.id.medal_title);
        }
    }

    @Override @NonNull
    public MedalsItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ArrayList<Integer> countM = new ArrayList<>(medalsMap.values());
        // ArrayList<String> sM = new ArrayList<>(medalsMap.keySet());
        // if (countM.get(position) == 0) return null;
        String text = scoringEventsTitles.get(position).replace("SCORING_EVENT_","");
        text = text.replace("_"," ");
        holder.medalCount.setText(String.valueOf(countM.get(position)));
        holder.medalTitle.setText(text);
        // medalTitle.setText(sM.get(position));
        holder.medalImage.setImageDrawable(dta.getMedalsImageTranslator(text.replace(" ","").toLowerCase()));
    }

    @Override
    public int getItemCount() {
        return scoringEventsTitles.size();
    }
}







