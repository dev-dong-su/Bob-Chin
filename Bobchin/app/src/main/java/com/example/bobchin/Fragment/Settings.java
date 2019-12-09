package com.example.bobchin.Fragment;

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
import com.example.bobchin.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Settings extends Fragment {

    static Bitmap bitmap;
    Handler handler;
    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        handler = new Handler();

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
        Glide.with(getActivity())
                .load(userInfo.getUserPhotoURL())
                .placeholder(R.drawable.ic_person)
                .apply(new RequestOptions().circleCrop())
                .into(userPhoto);

        Button btnSignout = v.findViewById(R.id.btnsignout);
        btnSignout.setOnClickListener((view)->{
            getActivity().finish();
        });
        return v;
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}

