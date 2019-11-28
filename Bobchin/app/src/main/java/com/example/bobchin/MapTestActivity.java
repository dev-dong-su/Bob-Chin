package com.example.bobchin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapTestActivity extends AppCompatActivity implements MapView.MapViewEventListener, View.OnClickListener {

    MapPOIItem marker;
    Button addMapPinButton;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

        MapView mapView = new MapView(this);
        mapView.setMapViewEventListener(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        addMapPinButton = findViewById(R.id.button_add_map_pin);
        addMapPinButton.setOnClickListener(this);
    }

    //MapViewEventListener 구현
    @Override
    public void onMapViewInitialized(MapView mapView) { }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        if(marker != null) {
            mapView.removePOIItem(marker);
            marker = null;
        }
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) { }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        marker = new MapPOIItem();
        marker.setItemName("모임 위치");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

        MapPoint.GeoCoordinate geoCoordinate = mapPoint.getMapPointGeoCoord();
        latitude = geoCoordinate.latitude;
        longitude = geoCoordinate.longitude;
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) { }

    //OnClickListener 구현
    @Override
    public void onClick(View view) {
        if(marker == null) {
            Toast.makeText(this, "마커를 만들어 주세요!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "마커를 확인했어요! latitude : " + latitude +" longitude : " + longitude, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), AddMeetingTestActivity.class);
            intent.putExtra("Latitude", latitude);
            intent.putExtra("Longitude", longitude);
            startActivity(intent);
        }
    }
}
