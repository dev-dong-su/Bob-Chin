package com.example.bobchin;

import androidx.recyclerview.widget.RecyclerView;

public class AlarmInfo {
    public String title;
    public String body;
    public int drawableId;

    public AlarmInfo(String title, String body, int drawableId) {
        this.title = title;
        this.body = body;
        this.drawableId = drawableId;

        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
    }
}
