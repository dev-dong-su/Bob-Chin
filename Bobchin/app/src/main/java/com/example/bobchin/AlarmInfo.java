package com.example.bobchin;

import androidx.recyclerview.widget.RecyclerView;

public class AlarmInfo {
    public String title;
    public String time;
    public double latitude;
    public double longitude;
    public int drawableId;

    public AlarmInfo(String title, String time, double latitude, double longitude, int drawableId) {
        this.title = title;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.drawableId = drawableId;

        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
    }
}
