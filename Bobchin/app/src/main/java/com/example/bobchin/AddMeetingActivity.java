package com.example.bobchin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobchin.Networking.HttpPost;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 100,REQCODE_MAP=101;

    double latitude;
    double longitude;

    CalendarView calendarView;

    EditText titleEditText;
    EditText maxPeopleEditText;
    EditText contentEditText;

    EditText EditFromAge;
    EditText EditToAge;
    EditText EditTimeHour;
    EditText EditTimeMinute;
    EditText EditDuration;

    Button mapButton;
    Button pictureButton;

    ImageView imageView;

    String url;
    String year,month,day;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == 0) {
                url = data.getStringExtra("url");
                Log.d("AddMeetingActivity", "사진 URL : " + url);
                pictureButton.setText("선택완료");
            }
            else if (resultCode == 999) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == REQCODE_MAP){
            if(resultCode == RESULT_OK){
                mapButton.setText("선택완료");
                latitude = data.getDoubleExtra("Latitude", 0.0);
                longitude = data.getDoubleExtra("Longitude", 0.0);
            }
        }
    }
    /*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);

        geoTextView = findViewById(R.id.text_geo);
        geoTextView.setText("선택한 좌표 : Latitude = " + latitude + ", Longitude = " + longitude);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);


        mapButton = findViewById(R.id.map_button);
        Button findBobchinButton = findViewById(R.id.button_find_bobchin);
        pictureButton = findViewById(R.id.button_add_picture);
        mapButton.setOnClickListener(this);
        findBobchinButton.setOnClickListener(this);
        pictureButton.setOnClickListener(this);

        //날짜 설정
        //현재날짜 일단 삽입(초기화)
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        year= yearFormat.format(currentTime);
        month= monthFormat.format(currentTime);
        day= dayFormat.format(currentTime);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int cyear, int cmonth, int cdayOfMonth) {
                cmonth++;
                year = Integer.toString(cyear);
                month = Integer.toString(cmonth).length()==1?"0"+cmonth:Integer.toString(cmonth);
                day = Integer.toString(cdayOfMonth).length()==1?"0"+cdayOfMonth:Integer.toString(cdayOfMonth);
            }
        });

        EditFromAge = findViewById(R.id.edit_from_age);
        EditToAge = findViewById(R.id.edit_to_age);
        EditTimeHour = findViewById(R.id.edit_start_hour);
        EditTimeMinute = findViewById(R.id.edit_start_minute);
        EditDuration = findViewById(R.id.edit_duration);

        EditTimeHour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(EditTimeHour.getText().toString().length() == 1)
                        EditTimeHour.setText("0"+EditTimeHour.getText().toString());
                }
            }
        });
        EditTimeMinute.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(EditTimeMinute.getText().toString().length() == 1)
                        EditTimeMinute.setText("0"+EditTimeMinute.getText().toString());
                }
            }
        });

        /*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromAge.setAdapter(adapter);
        spinnerToAge.setAdapter(adapter);

        spinnerTimeHour = findViewById(R.id.spinner_time_hour);
        spinnerTimeMinute = findViewById(R.id.spinner_time_minute);
        spinnerDuration = findViewById(R.id.spinner_duration);

        adapter = ArrayAdapter.createFromResource(this, R.array.hour, android.R.layout.simple_spinner_item);
        spinnerTimeHour.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this, R.array.minute, android.R.layout.simple_spinner_item);
        spinnerTimeMinute.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this, R.array.duration, android.R.layout.simple_spinner_item);
        spinnerDuration.setAdapter(adapter);*/
        /*
        Intent intent = getIntent();

        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);

        TextView geoTextView = findViewById(R.id.text_geo);
        geoTextView.setText("선택한 좌표 : Latitude = " + latitude + ", Longitude = " + longitude);*/

        titleEditText = findViewById(R.id.edit_text_title);
        maxPeopleEditText = findViewById(R.id.edit_text_max_people);
        contentEditText = findViewById(R.id.edit_text_content);

        imageView = findViewById(R.id.image_example);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_button) {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivityForResult(intent,REQCODE_MAP);
        }
        else if (view.getId() == R.id.button_find_bobchin) {
            // 서버로 보낼 값들
            String title;
            String content;
            String ageMax;
            String ageMin;
            String location;
            String startTime;
            String duration;
            String maxPeople;

            title = titleEditText.getText().toString();
            content = contentEditText.getText().toString();
            ageMax = EditToAge.getText().toString();//getSelectedItem().toString();
            ageMin = EditFromAge.getText().toString();//getSelectedItem().toString();
            location = longitude + ", " + latitude;
            startTime = year +  "-" + month + "-" + day + " " + EditTimeHour.getText().toString() + ":" + EditTimeMinute.getText().toString() + ":00";
            Log.e("date",startTime);
            duration = EditDuration.getText().toString();
            maxPeople = maxPeopleEditText.getText().toString();

            if ((Integer.parseInt(ageMax) >= Integer.parseInt(ageMin)) && (longitude != 0) && (latitude != 0) && (Integer.parseInt(maxPeople) > 0) && (url != null)) {
                BobChin bobChin = (BobChin) getApplicationContext();
                // 서버 통신
                try {
                    HttpPost httpPost = new HttpPost();
                    httpPost.execute("http://bobchin.cf/api/addmeet.php", "token=" + bobChin.getUserInfoObj().getUserAccessToken() + "&meetname=" + title + "&meetmsg=" + content + "&agemax=" + ageMax + "&agemin=" + ageMin + "&location=" + location + "&starttime=" + startTime + "&duration=" + duration + "&maxpeople=" + maxPeople + "&photo="+ url);
                    Log.d("AddMeetingActivity", "add result : " + startTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // 메인으로 돌아가는 코드
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
            else if (Integer.parseInt(ageMax) < Integer.parseInt(ageMin)) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("모임 생성 실패")
                        .setMessage("나이를 다시 설정해주세요!(연령대가 바뀌었는지 확인)")
                        .setPositiveButton("네", null)
                        .show();
            }
            else if ((longitude == 0) || (latitude == 0)) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("모임 생성 실패")
                        .setMessage("모임 위치를 지정해주세요!")
                        .setPositiveButton("네", null)
                        .show();
            }
            else if (Integer.parseInt(maxPeople) <= 0) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("모임 생성 실패")
                        .setMessage("모임 사람의 인원을 설정해주세요!")
                        .setPositiveButton("네", null)
                        .show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("모임 생성 실패")
                        .setMessage("알 수 없는 문제가 발생했습니다.")
                        .setPositiveButton("네", null)
                        .show();
            }
        }
        else if (view.getId() == R.id.button_add_picture) {
                Intent imageIntent = new Intent(getApplicationContext(), ImagePicker.class);
                startActivityForResult(imageIntent, REQUEST_CODE);
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
        findViewById(R.id.button_alarm).setVisibility(View.GONE);

        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("저장하지 않고 종료")
                .setMessage("모임을 만들지 않고 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}