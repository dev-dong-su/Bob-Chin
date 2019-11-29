package com.example.bobchin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddMeetingTestActivity extends AppCompatActivity implements View.OnClickListener {
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting_test);

        Button buttonMap = findViewById(R.id.map_button);
        buttonMap.setOnClickListener(this);

        Spinner spinnerFromAge = findViewById(R.id.spinner_form_age);
        Spinner spinnerToAge = findViewById(R.id.spinner_to_age);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromAge.setAdapter(adapter);
        spinnerToAge.setAdapter(adapter);

        Spinner spinnerSetTime = findViewById(R.id.spinner_set_time);
        Spinner spinnerFromTime = findViewById(R.id.spinner_form_time);
        Spinner spinnerToTime = findViewById(R.id.spinner_to_time);

        adapter = ArrayAdapter.createFromResource(this, R.array.set_time, android.R.layout.simple_spinner_item);
        spinnerSetTime.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        spinnerFromTime.setAdapter(adapter);
        spinnerToTime.setAdapter(adapter);

        Intent intent = getIntent();

        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);

        TextView geoTextView = findViewById(R.id.text_geo);
        geoTextView.setText("선택한 좌표 : Latitude = " + latitude + ", Longitude = " + longitude);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_button) {
            Intent intent = new Intent(getApplicationContext(), MapTestActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_find_bobchin) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionbar,params);

        return true;
    }
}
