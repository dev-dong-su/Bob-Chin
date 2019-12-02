package com.example.bobchin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobchin.Adapter.MyAdapter;
import com.example.bobchin.BobChin;
import com.example.bobchin.HttpGet;
import com.example.bobchin.MeetInfo;
import com.example.bobchin.R;
import com.example.bobchin.select_meeting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Meetings extends Fragment {

    public Meetings() {
        // Required empty public constructor
    }
    static String result="";
    ArrayList<MeetInfo> meetInfoArrayList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meetings, container, false);


        mRecyclerView = v.findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        meetInfoArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(meetInfoArrayList);
        SwipeRefreshLayout swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout_1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setResultNull();
                Refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        Refresh();

        return v;
    }

    public void setResultNull(){
        result = "";
    }

    public void Refresh(){
        try{
            BobChin bobChin = (BobChin)getActivity().getApplicationContext();
            BobChin.UserInfo userInfo = bobChin.getUserInfoObj();
            HttpGet httpGet = new HttpGet();

            meetInfoArrayList.clear();
            if(result.isEmpty())
                result = httpGet.execute("http://bobchin.cf/api/getbbs.php?token="+userInfo.getUserAccessToken()).get();
            System.out.println(result);
            JSONArray jsonArray = new JSONArray(result);
            System.out.println(jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String [] users = jsonObject.getString("users").split("\\|");
                meetInfoArrayList.add(new MeetInfo(jsonObject.getString("meetname"), jsonObject.getString("location"),jsonObject.getString("starttime")+", "+jsonObject.getString("duration"),(users.length-1)+"/"+jsonObject.getString("maxpeople"),"#"+jsonObject.getString("agemin")+"~"+jsonObject.getString("agemax")+"세만",jsonObject.getString("meetID"),jsonObject.getString("meetmsg"),users));
            }

            mRecyclerView.setAdapter(myAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

