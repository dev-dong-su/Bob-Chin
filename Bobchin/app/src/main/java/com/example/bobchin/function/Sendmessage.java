package com.example.bobchin.function;

import com.example.bobchin.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sendmessage {
    public Sendmessage(String sender,String text,String meetid){

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Message message = new Message(sender,text);
        String key = mDatabase.child("messages").child(meetid).push().getKey();
        mDatabase.child("messages").child(meetid+"/"+key).setValue(message);
        mDatabase.child("roominfo").child(meetid).setValue(message);
    }
}
