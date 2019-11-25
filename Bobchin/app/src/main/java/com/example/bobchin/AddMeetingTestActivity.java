package com.example.bobchin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddMeetingTestActivity extends AppCompatActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_button) {
            Intent intent = new Intent(getApplicationContext(), MapTestActivity.class);
            startActivity(intent);
        }
    }
}
