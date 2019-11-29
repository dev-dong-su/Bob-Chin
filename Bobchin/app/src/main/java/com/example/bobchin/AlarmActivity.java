package com.example.bobchin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.bobchin.Adapter.AlarmAdapter;
import com.example.bobchin.Adapter.MyAdapter;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_alarm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(mLayoutManager);

        ArrayList<AlarmInfo> alarmInfoArrayList = new ArrayList<>();
        alarmInfoArrayList.add(new AlarmInfo("국밥먹을사람", "15:00",126.959811, 37.494512, R.drawable.bread));
        alarmInfoArrayList.add(new AlarmInfo("국밥먹을사람", "15:00",126.959811, 37.494512, R.drawable.bread));
        alarmInfoArrayList.add(new AlarmInfo("국밥먹을사람", "15:00",126.959811, 37.494512, R.drawable.bread));
        alarmInfoArrayList.add(new AlarmInfo("국밥먹을사람", "15:00",126.959811, 37.494512, R.drawable.bread));

        AlarmAdapter alarmAdapter = new AlarmAdapter(alarmInfoArrayList);
        recyclerView.setAdapter(alarmAdapter);
    }
}
