package com.example.bobchin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bobchin.AlarmInfo;
import com.example.bobchin.R;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView alarmContent;
        ImageView titleImage;
        ViewGroup mapViewContainer;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.alarm_title);
            alarmContent = itemView.findViewById(R.id.alarm_content);
            titleImage = itemView.findViewById(R.id.alarm_image);
            mapViewContainer = itemView.findViewById(R.id.map_view_alarm);
        }
    }

    private ArrayList<AlarmInfo> AlarmInfoArrayList;

    public AlarmAdapter(ArrayList<AlarmInfo> AlarmInfoArrayList){
        this.AlarmInfoArrayList = AlarmInfoArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlarmViewHolder alarmViewHolder = (AlarmViewHolder) holder;
        alarmViewHolder.title.setText(AlarmInfoArrayList.get(position).title);
        alarmViewHolder.alarmContent.setText(AlarmInfoArrayList.get(position).body);
        alarmViewHolder.titleImage.setImageResource(AlarmInfoArrayList.get(position).drawableId);
    }

    @Override
    public int getItemCount() {
        return AlarmInfoArrayList.size();
    }
}
