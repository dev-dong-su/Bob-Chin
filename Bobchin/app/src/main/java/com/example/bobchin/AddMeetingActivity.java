package com.example.bobchin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 0;

    double latitude;
    double longitude;

    TextView geoTextView;

    EditText titleEditText;
    EditText maxPeopleEditText;
    EditText contentEditText;

    Spinner spinnerFromAge;
    Spinner spinnerToAge;
    Spinner spinnerTimeHour;
    Spinner spinnerTimeMinute;
    Spinner spinnerDuration;

    ImageView imageView;

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());

                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();

                    imageView.setImageBitmap(bitmap);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);

        geoTextView = findViewById(R.id.text_geo);
        geoTextView.setText("선택한 좌표 : Latitude = " + latitude + ", Longitude = " + longitude);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        Button mapButton = findViewById(R.id.map_button);
        Button findBobchinButton = findViewById(R.id.button_find_bobchin);
        Button pictureButton = findViewById(R.id.button_add_picture);
        mapButton.setOnClickListener(this);
        findBobchinButton.setOnClickListener(this);
        pictureButton.setOnClickListener(this);

        spinnerFromAge = findViewById(R.id.spinner_form_age);
        spinnerToAge = findViewById(R.id.spinner_to_age);

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
        spinnerDuration.setAdapter(adapter);

        Intent intent = getIntent();

        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);

        TextView geoTextView = findViewById(R.id.text_geo);
        geoTextView.setText("선택한 좌표 : Latitude = " + latitude + ", Longitude = " + longitude);

        titleEditText = findViewById(R.id.edit_text_title);
        maxPeopleEditText = findViewById(R.id.edit_text_max_people);
        contentEditText = findViewById(R.id.edit_text_content);

        imageView = findViewById(R.id.image_example);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_button) {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_find_bobchin) {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
            SimpleDateFormat monthFormat = new SimpleDateFormat("mm", Locale.getDefault());
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

            String year = yearFormat.format(currentTime);
            String month = monthFormat.format(currentTime);
            String day = dayFormat.format(currentTime);

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
            ageMax = spinnerToAge.getSelectedItem().toString();
            ageMin = spinnerFromAge.getSelectedItem().toString();
            location = longitude + ", " + latitude;
            startTime = year +  "-" + month + "-" + day + " " + spinnerTimeHour.getSelectedItem().toString() + ":" + spinnerTimeMinute.getSelectedItem().toString() + ":00";
            duration = spinnerDuration.getSelectedItem().toString();
            maxPeople = maxPeopleEditText.getText().toString();

            if ((Integer.parseInt(ageMax) < Integer.parseInt(ageMin)) && (longitude != 0) && (latitude != 0) && (Integer.parseInt(maxPeople) > 0) && (bitmap != null)) {
                BobChin bobChin = (BobChin) getApplicationContext();
                // 서버 통신
                try {
                    HttpPost httpPost = new HttpPost();
                    String result = httpPost.execute("http://bobchin.cf/api/addmeet.php", "token=" + bobChin.getUserInfoObj().getUserAccessToken() + "&meetname=" + title + "&meetmsg=" + content + "&agemax=" + ageMax + "&agemin=" + ageMin + "&location=" + location + "&starttime=" + startTime + "&duration=" + duration + "&maxpeople=" + maxPeople).get();
                    Log.d("AddMeetingActivity", "result : " + result);

                    // 이미지 전송 코드 예정
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 메인으로 돌아가는 코드
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (Integer.parseInt(ageMax) > Integer.parseInt(ageMin)) {
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
            else if (bitmap == null) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("모임 생성 실패")
                        .setMessage("모임 사진을 넣어주세요!")
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
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddMeetingActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else {
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imageIntent, REQUEST_CODE);
            }
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("저장하지 않고 종료")
                .setMessage("모임을 만들지 않고 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }
}