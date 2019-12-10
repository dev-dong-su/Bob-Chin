package com.example.bobchin;

import java.io.Serializable;

public class MeetInfo_Serialized implements Serializable { //아이템정보 입력할때 쓰는 클래스임.
    public String title;
    public String address;
    public String time;
    public String person;
    public String age;
    public String meetid;
    public String meetmsg;
    public String [] users;
    public Boolean isUser;
    public String foodimageUrl;

    public MeetInfo_Serialized(MeetInfo meetInfo){
        this.title=meetInfo.title;
        this.address = meetInfo.address;
        this.time=meetInfo.time;
        this.person=meetInfo.person;
        this.age=meetInfo.age;
        this.meetid=meetInfo.meetid;
        this.meetmsg=meetInfo.meetmsg;
        this.users = meetInfo.users;
        this.isUser = meetInfo.isUser;
        this.foodimageUrl = meetInfo.foodimageUrl;
    }

    public MeetInfo_Serialized(String foodimageUrl, String title, String address, String time, String person, String age, String meetid, String meetmsg, String [] users, Boolean isUser){
        this.title=title;
        this.address = address;
        this.time=time;
        this.person=person;
        this.age=age;
        this.meetid=meetid;
        this.meetmsg=meetmsg;
        this.users = users;
        this.isUser = isUser;
        this.foodimageUrl=foodimageUrl;

    }

    public MeetInfo_Serialized(String foodimageUrl, String title, String address, String time, String person, String age, String meetid, String meetmsg, String [] users){
        this.title=title;
        this.address = address;
        this.time=time;
        this.foodimageUrl=foodimageUrl;
        this.person=person;
        this.age=age;
        this.meetid=meetid;
        this.meetmsg=meetmsg;
        this.users = users;
    }
}

