package com.example.bobchin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobchin.Adapter.MyAdapter;
import com.example.bobchin.MeetInfo;
import com.example.bobchin.R;

import java.util.ArrayList;


public class Mymeetings extends Fragment {

    public Mymeetings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        View v = inflater.inflate(R.layout.fragment_mymeetings, container, false);
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<MeetInfo> meetInfoArrayList = new ArrayList<>();
        meetInfoArrayList.add(new MeetInfo("국밥먹을사람", "ㄷㅁㄹㄷㅈㅁㅈㄷㄻㅈㄷㄻㅈㄷㄻㅈㄷㄻㅈㄷㄹㄻㅈ","12:00","1/5","#20~25"));
        meetInfoArrayList.add(new MeetInfo("곱창팟", "ㄷㅁㄹㄷㅈㄻㅈ","15:00","3/5","#10~25"));
        meetInfoArrayList.add(new MeetInfo("치맥먹을사라암", "숭실대학교 나무계단","18:00","1/5","#20~25"));
        meetInfoArrayList.add(new MeetInfo("오롤롤롤롤ㄹ롤롤", "서울특별시 상도로369 숭실대 전산관 지하1층 스페이스엔","15:00","1/5","#20~25"));
        meetInfoArrayList.add(new MeetInfo("홓로로로로롤ㄹ롤", "숭실대학교 나무계단 옆 나무 위 세번째 계단 밑 두번째 계단","17:00","3/5","#20~25"));

        MyAdapter myAdapter = new MyAdapter(meetInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);

        return v;
    }
}

