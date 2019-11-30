package com.example.bobchin.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bobchin.BobChin;
import com.example.bobchin.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Settings extends Fragment {

    static Bitmap bitmap;
    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        BobChin bobChin = (BobChin)getActivity().getApplicationContext();
        BobChin.UserInfo userInfo = bobChin.getUserInfoObj();

        TextView name = v.findViewById(R.id.name);
        name.setText(userInfo.getUserName());

        TextView email = v.findViewById(R.id.email);
        email.setText(userInfo.getUserEmail());

        TextView authLevel = v.findViewById(R.id.auth_level);
        String strAuthLevel = "";
        switch(userInfo.getUserAuthLevel()){
            case "1":
                strAuthLevel = "회원";
                break;
            case "2":
                strAuthLevel = "관리자";
                break;
            case "3":
                strAuthLevel = "총관리자";
                break;
        }
        authLevel.setText(strAuthLevel);

        ImageView userPhoto = v.findViewById(R.id.imageView5);
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(userInfo.getUserPhotoURL());

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    if(bitmap == null) {
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // 서버로 부터 응답 수신
                        conn.connect();

                        InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                        bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start(); // Thread 실행
        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            userPhoto.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button btnSignout = v.findViewById(R.id.btnsignout);
        btnSignout.setOnClickListener((view)->{
            getActivity().finish();
        });
        return v;
    }
}

