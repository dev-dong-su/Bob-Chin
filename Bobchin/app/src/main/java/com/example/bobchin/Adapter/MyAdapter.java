package com.example.bobchin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bobchin.BobChin;
import com.example.bobchin.Fragment.Meetings;
import com.example.bobchin.MeetInfo;
import com.example.bobchin.MeetInfo_Serialized;
import com.example.bobchin.R;
import com.example.bobchin.activity_chatroom;
import com.example.bobchin.select_meeting;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView address;
        TextView time;
        TextView person;
        TextView age;
        MeetInfo_Serialized data;
        TextView count;
        ImageView foodimage;

        MyViewHolder(View view){
            super(view);
            foodimage=view.findViewById(R.id.foodimage);
            title = view.findViewById(R.id.meeting_title);
            address = view.findViewById(R.id.address);
            time = view.findViewById(R.id.meet_time);
            person = view.findViewById(R.id.meet_person);
            age=view.findViewById(R.id.age);
            count=view.findViewById(R.id.seencount);
        }
    }

    private ArrayList<MeetInfo> MeetInfoArrayList;
    private String email;
    public MyAdapter(ArrayList<MeetInfo> MeetInfoArrayList,String email){
        this.email=email;
        this.MeetInfoArrayList = MeetInfoArrayList;
    }

    @Override // oncreateViewHolder는 아이템레이아웃을 뷰홀더에 고정시키는 역할을 함.
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override // onBindViewHolder는 뷰홀더에 데이터를 입력시킴.
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.title.setText(MeetInfoArrayList.get(position).title);
        myViewHolder.address.setText(MeetInfoArrayList.get(position).region);
        myViewHolder.time.setText(MeetInfoArrayList.get(position).time);
        myViewHolder.person.setText(MeetInfoArrayList.get(position).person);
        myViewHolder.age.setText(MeetInfoArrayList.get(position).age);
        Glide.with(holder.itemView.getContext())
                .load(MeetInfoArrayList.get(position).foodimageUrl)
                .placeholder(R.drawable.bread)
                .into(myViewHolder.foodimage);
        myViewHolder.data = new MeetInfo_Serialized(MeetInfoArrayList.get(position));

        final ArrayList<String> total=new ArrayList<>();
        final ArrayList<String> read=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("messages/"+MeetInfoArrayList.get(position).meetid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total.clear();
                read.clear();
                for(DataSnapshot aa:dataSnapshot.getChildren()){
                    total.add("d");
                    System.out.println("토탈추가:"+total.size());
                    if(aa.child("readuser").toString().contains(email)) {
                        read.add("d");
                        System.out.println("개별추가:"+read.size());                    }
                }
                int count = total.size()-read.size();
                if(count!=0) {
                    myViewHolder.count.setVisibility(View.VISIBLE);
                    myViewHolder.count.setText(String.valueOf(count));
                }
                else if(count==0)
                    myViewHolder.count.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(v.getContext(),myViewHolder.data.meetid,Toast.LENGTH_LONG).show();
                        int position = holder.getAdapterPosition();
                        Intent intent = new Intent(v.getContext(), select_meeting.class);
                        intent.putExtra("class", myViewHolder.data);
                        ((Activity) v.getContext()).startActivityForResult(intent, 1);
                    }
                });
    }

    @Override // 사이즈 확인
    public int getItemCount() {
        return MeetInfoArrayList.size();
    }

    public MeetInfo getNthItem(int i){
        return MeetInfoArrayList.get(i);
    }
}
