package com.mrsaad.hackwestern;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by saad on 15-11-20.
 */
public class HackathonConstants {

    private String hackathonEndTimeString = "11/29/2015 10:00 am";
    public Date hackathonEndTime;
    public static Context appContext;

    public HackathonConstants(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.CANADA);
        try{
            hackathonEndTime = dateFormat.parse(hackathonEndTimeString);
        }catch(Exception e){
            Log.v("DEBUG", e.getMessage());
            hackathonEndTime = new Date();
        }

    }

    public long timeLeftInSeconds(){
        //get difference in seconds
        Date currTime = new Date();
        long diff = hackathonEndTime.getTime() - currTime.getTime();
        long diffSec = diff/1000;

        //truncate to show relevant time
        if(diffSec > 34.5*3600){diffSec = (long)(34.5*3600);}
        if(diffSec < 0){diffSec = 0;}

        return diffSec;
    }


    public String countdownText(){

        long diffSec = timeLeftInSeconds();

        //format things
        String hours = diffSec/3600 < 10 ? "0"+diffSec/3600 : ""+diffSec/3600;
        String mins = diffSec/60%60 < 10 ? "0"+diffSec/60%60 : ""+diffSec/60%60;
        String secs = diffSec%60 < 10 ? "0"+diffSec%60 : ""+diffSec%60;

        return hours+":"+mins+":"+secs;
    }

    public String countdownHour(){
        long diffSec = timeLeftInSeconds();
        String hours = diffSec/3600 < 10 ? "0"+diffSec/3600 : ""+diffSec/3600;
        return hours;
    }

    public int countdownTextColor(){
        double myRed, myGreen;

        //get difference in seconds
        Date currTime = new Date();
        long diff = hackathonEndTime.getTime() - currTime.getTime();
        long diffSec = diff/1000;

        //keep green if over 36 hours
        if (diffSec > 36 * 3600){
            myRed = 0;
            myGreen = 255;
        }
        //change from green to yellow in first half
        else if (diffSec <= 36 * 3600 && diffSec > 18 * 3600){
            myRed = 255 - 255*(double)(diffSec-18*3600)/(18*3600);
            myGreen = 255;
        }
        //change from yellow to red in second half
        else if(diffSec <= 18 * 3600 && diffSec > 0){
            myGreen = 255*(double)(diffSec)/(18*3600);
            myRed = 255;
        }
        //keep red if less than 0
        else{
            myRed = 255;
            myGreen = 0;
        }

        return Color.rgb((int)myRed, (int)myGreen, 0);

    }



}

