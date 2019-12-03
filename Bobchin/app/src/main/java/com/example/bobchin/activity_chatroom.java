package com.example.bobchin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bobchin.Adapter.ChatAdapter;
import com.example.bobchin.function.Sendmessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_chatroom extends AppCompatActivity {
    TextView name1;
    Button snd_button;
    EditText send_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chatroom);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        BobChin bobChin = (BobChin)getApplicationContext();
        BobChin.UserInfo userInfo = bobChin.getUserInfoObj();
        name1=findViewById(R.id.oppositename);
        name1.setText(getIntent().getStringExtra("title"));
        String meetid=getIntent().getStringExtra("meetid");
        final RecyclerView mRecyclerView = findViewById(R.id.recycler_view1);
        final RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DatabaseReference mDatabase;
        final ArrayList<Message> messages = new ArrayList<>();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("messages/"+meetid);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatAdapter chatAdapter = new ChatAdapter(messages, userInfo.getUserName());
                mRecyclerView.setAdapter(chatAdapter);
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(new Message(message.Sender,message.message));
                chatAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPosition(messages.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        snd_button=findViewById(R.id.send_btn);
        send_txt=findViewById(R.id.send_txt);
        snd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        new Sendmessage(userInfo.getUserName(),send_txt.getText().toString(),meetid);
            }
        });

    }
}
