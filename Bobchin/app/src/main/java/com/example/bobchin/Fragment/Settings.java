package com.example.bobchin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bobchin.BobChin;
import com.example.bobchin.R;

import org.w3c.dom.Text;


public class Settings extends Fragment {

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

        Button btnSignout = v.findViewById(R.id.btnsignout);
        btnSignout.setOnClickListener((view)->{
            getActivity().finish();
        });
        return v;
    }
}

