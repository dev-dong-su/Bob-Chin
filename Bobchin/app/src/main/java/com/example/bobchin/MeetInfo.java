package com.example.bobchin;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

public class MeetInfo { //아이템정보 입력할때 쓰는 클래스임.
    public String title;
    public String address;
    public String time;
    public String person;
    public String age;
    public String meetid;
    public String meetmsg;
    public String [] users;
    public Boolean isUser;
    public Bitmap foodimage;

    public MeetInfo(MeetInfo_Serialized meetInfoSerialized){
        this.title=meetInfoSerialized.title;
        this.address = meetInfoSerialized.address;
        this.time=meetInfoSerialized.time;
        this.person=meetInfoSerialized.person;
        this.age=meetInfoSerialized.age;
        this.meetid=meetInfoSerialized.meetid;
        this.meetmsg=meetInfoSerialized.meetmsg;
        this.users = meetInfoSerialized.users;
        this.isUser = meetInfoSerialized.isUser;
    }
    public MeetInfo(Bitmap foodimage,String title, String address, String time, String person, String age, String meetid,String meetmsg,String [] users, Boolean isUser){
        this.title=title;
        this.address = address;
        this.time=time;
        this.person=person;
        this.age=age;
        this.meetid=meetid;
        this.meetmsg=meetmsg;
        this.users = users;
        this.isUser = isUser;
        this.foodimage=foodimage;

    }

    public MeetInfo(Bitmap foodimage,String title, String address, String time, String person, String age, String meetid,String meetmsg,String [] users){
        this.title=title;
        this.address = address;
        this.time=time;
        this.foodimage=foodimage;
        this.person=person;
        this.age=age;
        this.meetid=meetid;
        this.meetmsg=meetmsg;
        this.users = users;
    }
}

