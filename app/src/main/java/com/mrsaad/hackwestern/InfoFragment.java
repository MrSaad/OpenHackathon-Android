package com.mrsaad.hackwestern;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFragment extends Fragment {

    Button websiteButton, devpostButton, showcaseButton;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set view elements
        final View root = inflater.inflate(R.layout.fragment_info, container, false);
        websiteButton = (Button)root.findViewById(R.id.website_button);
        devpostButton = (Button)root.findViewById(R.id.devpost_button);
        showcaseButton = (Button)root.findViewById(R.id.showcase_button);

        //set button actions
        websiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToUrl("https://hackwestern.com");
            }
        });
        devpostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToUrl("https://hackwestern.com/devpost-page-link");
            }
        });
        showcaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToUrl("https://hackwestern.com/showcase");
            }
        });

        return root;
    }


    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
