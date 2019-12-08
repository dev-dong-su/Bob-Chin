package com.example.bobchin.Fragment;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bobchin.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirstAdFragment extends Fragment {
    private String title;
    private int page;
    private ImageView imgAd;
    private Bitmap bitmap;

    public static FirstAdFragment newInstance(int page, String title) {
        FirstAdFragment fragment = new FirstAdFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad_first, container, false);
        imgAd = view.findViewById(R.id.imgAd);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bitmap = getBitmap(title);
                }
                catch(Exception e){ }
                finally{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgAd.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        }).start();

        return view;
    }

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        Bitmap retBitmap = null;
        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            retBitmap = BitmapFactory.decodeStream(is);
        }
        catch(Exception e) { e.printStackTrace(); return null; }
        finally {
            if(connection!=null) { connection.disconnect(); }
            return retBitmap;
        }
    }

}
