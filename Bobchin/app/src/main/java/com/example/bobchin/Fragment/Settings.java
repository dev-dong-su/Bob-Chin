package com.example.bobchin.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bobchin.BobChin;
import com.example.bobchin.ImagePicker;
import com.example.bobchin.Networking.HttpPost;
import com.example.bobchin.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import kotlin.jvm.internal.Ref;


public class Settings extends Fragment {

    Bitmap bitmap;
    Handler handler;
    BobChin.UserInfo userInfo;
    ImageView userPhoto;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        handler = new Handler();

        BobChin bobChin = (BobChin)getActivity().getApplicationContext();
        userInfo = bobChin.getUserInfoObj();

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

        userPhoto = v.findViewById(R.id.imageView5);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ImagePicker.class);
                ((Activity) v.getContext()).startActivityForResult(intent, 3);
            }
        });
        RefreshProfile();

        Button btnSignout = v.findViewById(R.id.btnsignout);
        btnSignout.setOnClickListener((view)->{
            getActivity().finish();
        });
        return v;
    }


    public void setMyProfile(String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpPost httpPost = new HttpPost();
                    httpPost.execute("http://bobchin.cf/api/setprofile.php", "token=" + userInfo.getUserAccessToken() + "&url=" + url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RefreshProfile();
                        }
                    });
                }
            }
        }).start();
    }

    public void RefreshProfile(){
        Glide.with(getActivity())
                .load(userInfo.getUserPhotoURL())
                .placeholder(R.drawable.ic_person)
                .apply(new RequestOptions().circleCrop())
                .into(userPhoto);
    }
}

