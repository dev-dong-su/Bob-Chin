package com.example.bobchin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends Activity implements MapView.MapViewEventListener, View.OnClickListener {

    MapView mapView;
    MapPOIItem marker;
    Button addMapPinButton;

    double latitude;
    double longitude;

    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }

        @Override
        public void onProviderEnabled(String s) { }

        @Override
        public void onProviderDisabled(String s) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = new MapView(this);
        mapView.setMapViewEventListener(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        addMapPinButton = findViewById(R.id.button_add_map_pin);
        addMapPinButton.setOnClickListener(this);

        ImageButton centerPointButton = findViewById(R.id.button_center_point);
        centerPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||  checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( MapActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
                }
                else {
                    final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    String locationProvider = LocationManager.NETWORK_PROVIDER;
                    Location location = locationManager.getLastKnownLocation(locationProvider);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
                }
            }
        });
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
        else{
            Intent intent = new Intent(getApplicationContext(), AddMeetingActivity.class);
            intent.putExtra("Latitude", latitude);
            intent.putExtra("Longitude", longitude);
            //startActivity(intent);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

}