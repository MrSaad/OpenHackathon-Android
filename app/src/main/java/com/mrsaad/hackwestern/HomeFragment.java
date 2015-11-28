package com.mrsaad.hackwestern;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class HomeFragment extends Fragment {

    //global constant functions
    HackathonConstants constants;

    //relevant text views
    TextView timerTextView;
    TextView complimentsTextView;

    //the compliments JSON
    JSONObject complimentsJSON;

    //timer handler variables
    Handler h;
    int updateDelay = 1000;

    public HomeFragment() {
        constants = new HackathonConstants();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set view elements
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        timerTextView = (TextView)root.findViewById(R.id.timer_text);
        complimentsTextView = (TextView)root.findViewById(R.id.compliment_text);

        //load compliments json
        try{
            complimentsJSON = new JSONObject(loadJSONFromAsset("compliments.json"));
        }catch(Exception e){e.printStackTrace();}

        //set initial layout data
        updateData();

        //set updater to update every second
        h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){

                //update the views with new data
                updateData();

                //tell handler to fire again after some time
                h.postDelayed(this, updateDelay);
            }
        }, updateDelay);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void updateData(){

        //set timer text and color
        timerTextView.setText(constants.countdownText());
        timerTextView.setTextColor(Color.parseColor("#673AB7"));

        //set compliment only if hackathon has started
        try{
            if(constants.timeLeftInSeconds() < 34.5*3600){
                complimentsTextView.setText(complimentsJSON.getString(constants.countdownHour()));
            }else{complimentsTextView.setText("...");}
        }catch(Exception e){e.printStackTrace();}
    }

    public String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = HackathonConstants.appContext.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
