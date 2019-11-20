package com.example.bobchin;

public class MeetInfo { //아이템정보 입력할때 쓰는 클래스임.
    public String title;
    public String address;
    public String time;
    public String person;
    public String age;

    public MeetInfo(String title, String address, String time, String person, String age){
        this.title=title;
        this.address = address;
        this.time=time;
        this.person=person;
        this.age=age;
    }
}

