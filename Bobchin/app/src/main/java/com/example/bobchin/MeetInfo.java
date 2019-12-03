package com.example.bobchin;

import java.io.Serializable;

public class MeetInfo implements Serializable { //아이템정보 입력할때 쓰는 클래스임.
    public String title;
    public String address;
    public String time;
    public String person;
    public String age;
    public String meetid;
    public String meetmsg;
    public String [] users;
<<<<<<< Updated upstream
=======
    public Boolean isUser;

    public MeetInfo(String title, String address, String time, String person, String age, String meetid,String meetmsg,String [] users, Boolean isUser){
        this.title=title;
        this.address = address;
        this.time=time;
        this.person=person;
        this.age=age;
        this.meetid=meetid;
        this.meetmsg=meetmsg;
        this.users = users;
        this.isUser = isUser;

    }
>>>>>>> Stashed changes

    public MeetInfo(String title, String address, String time, String person, String age, String meetid,String meetmsg,String [] users){
        this.title=title;
        this.address = address;
        this.time=time;
        this.person=person;
        this.age=age;
        this.meetid=meetid;
        this.meetmsg=meetmsg;
        this.users = users;
<<<<<<< Updated upstream
=======
        this.isUser = isUser;
>>>>>>> Stashed changes
    }
}

