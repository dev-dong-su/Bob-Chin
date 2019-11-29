package com.example.bobchin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bobchin.AddMeetingActivity;
import com.example.bobchin.R;


public class Meetings extends Fragment {

    public Meetings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);
        Button makeMeetingButton = view.findViewById(R.id.button_make_meeting);
        makeMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMeetingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

