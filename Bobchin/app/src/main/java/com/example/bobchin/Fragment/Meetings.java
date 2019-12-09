package com.example.bobchin.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bobchin.Adapter.MyAdapter1;
import com.example.bobchin.BobChin;
import com.example.bobchin.HttpGet;
import com.example.bobchin.MeetInfo;
import com.example.bobchin.MeetInfo_Serialized;
import com.example.bobchin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Meetings extends Fragment {

    public Meetings() {
        // Required empty public constructor
    }
    static String result="";
    ArrayList<MeetInfo> meetInfoArrayList;
    public RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public MyAdapter1 myAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meetings, container, false);


        mRecyclerView = v.findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        BobChin bobChin = (BobChin) getActivity().getApplicationContext();
        BobChin.UserInfo userInfo = bobChin.getUserInfoObj();
        meetInfoArrayList = new ArrayList<>();
        myAdapter = new MyAdapter1(meetInfoArrayList,userInfo.getUserEmail());
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout_1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setResultNull();
                Refresh();
            }
        });
        Refresh();

        return v;
    }

    public void setResultNull(){
        result = "";
    }

    public void Refresh(){
            swipeRefreshLayout.setRefreshing(true);
            BobChin bobChin = (BobChin) getActivity().getApplicationContext();
            BobChin.UserInfo userInfo = bobChin.getUserInfoObj();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpGet httpGet = new HttpGet();

                        meetInfoArrayList.clear();
                        if (result.isEmpty())
                            result = httpGet.execute("http://bobchin.cf/api/getbbs.php?token=" + userInfo.getUserAccessToken()).get();

                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String[] users = jsonObject.getString("users").split("\\|");
                            meetInfoArrayList.add(new MeetInfo(jsonObject.getString("photo"), jsonObject.getString("meetname"), jsonObject.getString("location"), jsonObject.getString("starttime") + ", " + jsonObject.getString("duration"), (users.length - 1) + "/" + jsonObject.getString("maxpeople"), "#" + jsonObject.getString("agemin") + "~" + jsonObject.getString("agemax") + "세만", jsonObject.getString("meetID"), jsonObject.getString("meetmsg"), users, jsonObject.getString("users").contains(userInfo.getUserEmail())));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.setAdapter(myAdapter);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }
            }).start();
    }

}

