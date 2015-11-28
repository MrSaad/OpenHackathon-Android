package com.mrsaad.hackwestern;

/**
 * Created by saad on 15-11-20.
 */
public class ScheduleItem {

    public String date;
    public String title;
    public String content;
    public String location;
    public int tag;
    public boolean isTitle;

    public ScheduleItem(String title, String date, String content, String location, int tag){
        this.date = date;
        this.title = title;
        this.content = content;
        this.isTitle = false;
        this.location = location;
        this.tag=tag;
    }

    public ScheduleItem(String title){
        this.date = "";
        this.title = title;
        this.content = "";
        this.location = "";
        this.isTitle = true;
        tag=0;
    }
}
