package com.example.bobchin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;

public class MapTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
    }
}
