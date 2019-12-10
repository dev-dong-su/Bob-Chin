package com.example.bobchin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bobchin.Networking.HttpPost;

import java.util.concurrent.ExecutionException;

public class select_meeting extends AppCompatActivity {

    String result;
    String msg = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meeting);
        TextView title = findViewById(R.id.title);
        TextView tags = findViewById(R.id.tags);
        TextView address = findViewById(R.id.address);
        TextView time = findViewById(R.id.time);
        TextView person = findViewById(R.id.person);
        TextView meetmsg = findViewById(R.id.meetmsg);
        Button btnEnterMeet = findViewById(R.id.entermeet);
        Button btnEnterChat = findViewById(R.id.enterchat);
        ImageView imgMeetPhoto = findViewById(R.id.MeetPhoto);

        Intent intent = getIntent();
        MeetInfo_Serialized meetInfo = (MeetInfo_Serialized) intent.getSerializableExtra("class");
        boolean entered = meetInfo.isUser;

        title.setText(meetInfo.title);
        tags.setText(meetInfo.age);
        address.setText(meetInfo.address);
        time.setText(meetInfo.time);
        person.setText(meetInfo.person);
        meetmsg.setText(meetInfo.meetmsg);
        Glide.with(this)
                .load(meetInfo.foodimageUrl)
                .into(imgMeetPhoto);

        if(entered){
            btnEnterMeet.setText("밥친 취소");
            btnEnterChat.setVisibility(View.VISIBLE);
        }

        btnEnterChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),activity_chatroom.class);
                intent.putExtra("title",meetInfo.title);
                intent.putExtra("meetid",meetInfo.meetid);

                startActivity(intent);
            }
        });

        btnEnterMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BobChin bobchin = (BobChin) getApplication();
                HttpPost httpPost = new HttpPost();
                msg = "";
                if (entered) {
                    httpPost.execute("http://bobchin.cf/api/outmeet.php", "token=" + bobchin.getUserInfoObj().getUserAccessToken() + "&meetid=" + meetInfo.meetid);
                    meetInfo.isUser = false;
                    msg = "밥친이 취소되었습니다.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    setResult(3);
                    finish();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                result = httpPost.execute("http://bobchin.cf/api/entermeet.php", "token=" + bobchin.getUserInfoObj().getUserAccessToken() + "&meetid=" + meetInfo.meetid).get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } finally {
                                setResult(Integer.parseInt(result));
                                finish();
                            }
                        }
                    }).start();

                }
            }
        });
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
    public void onBackPressed(){
        setResult(999);
        finish();
    }
}
