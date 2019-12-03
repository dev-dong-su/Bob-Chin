package com.example.bobchin.function;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bobchin.BobChin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Makechatroom {
    public Makechatroom(Context context){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        BobChin bobChin = (BobChin)context;
        BobChin.UserInfo userInfo = bobChin.getUserInfoObj();
        mDatabase.child("users").orderByChild("userid").equalTo(userInfo.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
