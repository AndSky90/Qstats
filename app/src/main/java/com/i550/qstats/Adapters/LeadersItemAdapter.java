package com.i550.qstats.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.qstats.Model.Entry;
import com.i550.qstats.R;

import java.util.List;


public class LeadersItemAdapter extends RecyclerView.Adapter<LeadersItemAdapter.LeadersViewHolder> {
    private static ClickListener clickListener;
    private Context context;
    private AssetDataTranslator dta;
    private List<Entry> leadsList;

    public LeadersItemAdapter(Context context, List<Entry> leadsList) {
        this.context = context;
        this.leadsList = leadsList;
        dta = AssetDataTranslator.getInstance(context);
    }

    static class LeadersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView namePlate;
        private ImageView profileIcon;
        private ImageView rangeIcon;
        private TextView profileName;
        private TextView duelElo;
        private TextView positionTextView;

        LeadersViewHolder(LayoutInflater i, ViewGroup parent) {
            super(i.inflate(R.layout.profile_item_view_holder, parent, false));

            namePlate = itemView.findViewById(R.id.namelpate);
            profileIcon = itemView.findViewById(R.id.profile_icon);
            rangeIcon = itemView.findViewById(R.id.range_icon);
            profileName = itemView.findViewById(R.id.profile_name);
            duelElo = itemView.findViewById(R.id.duel_elo);
            positionTextView = itemView.findViewById(R.id.position);

            itemView.setOnClickListener(this);
        }               //itemView - экземпляр данной вью

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(profileName.getText().toString(), v);
        }
    }

    @Override
    @NonNull
    public LeadersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LeadersViewHolder(LayoutInflater.from(context), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadersViewHolder holder, int position) {

        Entry entry = leadsList.get(position);

        holder.profileName.setText(entry.getUserName());
        holder.positionTextView.setText(String.valueOf(position + 1));
        int elo = entry.getEloRating();
        holder.duelElo.setText(String.valueOf(elo));
        holder.rangeIcon.setImageDrawable(dta.getRangeImageTranslator(elo));
        holder.profileIcon.setImageDrawable(dta.getIconsImageTranslator(entry.getProfileIconId()));
        holder.namePlate.setImageDrawable(dta.getNameplatesImageTranslator(entry.getNamePlateId()));
    }

    @Override
    public int getItemCount() {
        return leadsList.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LeadersItemAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(String name, View v);
    }
}
