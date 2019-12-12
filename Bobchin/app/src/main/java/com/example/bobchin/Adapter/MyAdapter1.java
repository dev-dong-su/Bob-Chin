package com.example.bobchin.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bobchin.MeetInfo;
import com.example.bobchin.MeetInfo_Serialized;
import com.example.bobchin.R;
import com.example.bobchin.select_meeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

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
            title = view.findViewById(R.id.meeting_title);
            address = view.findViewById(R.id.address);
            time = view.findViewById(R.id.meet_time);
            person = view.findViewById(R.id.meet_person);
            age=view.findViewById(R.id.age);
            count=view.findViewById(R.id.seencount);
            foodimage=view.findViewById(R.id.foodimage);
        }
    }

    private ArrayList<MeetInfo> MeetInfoArrayList;
    private String email;
    public MyAdapter1(ArrayList<MeetInfo> MeetInfoArrayList, String email){
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
