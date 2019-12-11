package com.example.bobchin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import net.daum.android.map.coord.MapCoord;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class ShowLocationActivity extends Activity {



    MapView mapView;
    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

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
        setContentView(R.layout.activity_show_location);


        Double x = getIntent().getDoubleExtra("x",0.0);
        Double y = getIntent().getDoubleExtra("y",0.0);

        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Bobchin", x+","+y);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(),"클립보드에 위치가 복사되었습니다!\n지도 앱에서 붙여넣기 하세요!",Toast.LENGTH_LONG).show();

        mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view_2);
        mapViewContainer.addView(mapView);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||  checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( ShowLocationActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
        }
        else {
            final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            Log.e("loca", ""+x+y+"");
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(y, x);
            mapView.setMapCenterPoint(mapPoint, true);

            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("Default Marker");
            marker.setTag(0);
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            mapView.addPOIItem(marker);
        }
    }
}
