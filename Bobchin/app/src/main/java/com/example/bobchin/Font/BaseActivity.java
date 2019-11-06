package com.example.bobchin.Font;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public Typeface mTypeface=null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (mTypeface == null)
            mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/basic.ttf");

        setGlobalFont(getWindow().getDecorView());
    }

    void setGlobalFont(View view) {
        if(view != null){
            if(view instanceof ViewGroup){
                ViewGroup vg=(ViewGroup) view;
                int vgCnt = vg.getChildCount();
                for(int i=0;i<vgCnt;i++){
                    View v = vg.getChildAt(i);
                    if(v instanceof TextView){
                        ((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}