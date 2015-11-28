package com.mrsaad.hackwestern;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;


public class MapsFragment extends Fragment {

    int selected;
    Button mapButton1, mapButton2, mapButton3;

    public MapsFragment() {
        selected = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set view elements
        final View root = inflater.inflate(R.layout.fragment_maps, container, false);
        mapButton1 = (Button)root.findViewById(R.id.map1);
        mapButton2 = (Button)root.findViewById(R.id.map2);
        mapButton3 = (Button)root.findViewById(R.id.map3);

        //set button actions
        mapButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected = 1;
                SubsamplingScaleImageView image = (SubsamplingScaleImageView)root.findViewById(R.id.mapView);
                image.setImage(ImageSource.asset("bf-ac-1_v2.png"));
                updateSelected();
            }
        });
        mapButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected = 2;
                SubsamplingScaleImageView image = (SubsamplingScaleImageView)root.findViewById(R.id.mapView);
                image.setImage(ImageSource.asset("bf-ac-2_v2.png"));
                updateSelected();
            }
        });
        mapButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected = 3;
                SubsamplingScaleImageView image = (SubsamplingScaleImageView)root.findViewById(R.id.mapView);
                image.setImage(ImageSource.asset("parking-althouse.png"));
                updateSelected();
            }
        });

        //set button selected
        updateSelected();

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateSelected() {
        switch (selected) {
            case 1:
                mapButton1.setBackgroundColor(Color.parseColor("#512DA8"));
                mapButton1.setTextColor(Color.WHITE);
                mapButton2.setBackgroundColor(Color.LTGRAY);
                mapButton2.setTextColor(Color.BLACK);
                mapButton3.setBackgroundColor(Color.LTGRAY);
                mapButton3.setTextColor(Color.BLACK);
                break;
            case 2:
                mapButton1.setBackgroundColor(Color.LTGRAY);
                mapButton1.setTextColor(Color.BLACK);
                mapButton2.setBackgroundColor(Color.parseColor("#512DA8"));
                mapButton2.setTextColor(Color.WHITE);
                mapButton3.setBackgroundColor(Color.LTGRAY);
                mapButton3.setTextColor(Color.BLACK);
                break;
            case 3:
                mapButton1.setBackgroundColor(Color.LTGRAY);
                mapButton1.setTextColor(Color.BLACK);
                mapButton2.setBackgroundColor(Color.LTGRAY);
                mapButton2.setTextColor(Color.BLACK);
                mapButton3.setBackgroundColor(Color.parseColor("#512DA8"));
                mapButton3.setTextColor(Color.WHITE);
                break;
        }
    }

}
