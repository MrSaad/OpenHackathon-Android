package com.mrsaad.hackwestern;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class Sponsors extends Fragment {

    public Sponsors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set view elements
        View root = inflater.inflate(R.layout.fragment_sponsors, container, false);

        //initialize items
        ArrayList<String> types = new ArrayList<String>(Arrays.asList("partners", "platinum", "gold", "silver", "bronze"));
        ArrayList<Object> items = new ArrayList<Object>();

        try{
            JSONObject obj = new JSONObject(loadJSONFromAsset("SponsorData.json"));
            obj = obj.getJSONObject("sponsors");

            //go through all the types
            for(String type: types){
                //add title
                items.add(type);
                JSONArray arr = obj.getJSONArray(type);
                ArrayList<SponsorItem> sponsorRow = new ArrayList<>();


                //add all items in the array of this type
                for(int i=0; i< arr.length(); i++){

                    //store as a SponsorRow of 5 elements
                    if(i!=0 && i%5==0){
                        items.add(new SponsorRow(sponsorRow));
                        sponsorRow = new ArrayList<>();
                    }

                    JSONObject currObj = arr.getJSONObject(i);
                    sponsorRow.add(new SponsorItem(currObj.getString("name"), currObj.getString("logo"), currObj.getString("info"),
                            currObj.getString("website")));
                }

                //add leftovers as SponsorRow as well
                if(sponsorRow.size()!=0){
                    items.add(new SponsorRow(sponsorRow));
                    sponsorRow = new ArrayList<>();
                }

            }
        }catch(Exception e){e.printStackTrace();}

        //initialize list view
        ListView listView = (ListView) root.findViewById(R.id.sponsor_list);
        listView.setDivider(null);
        listView.setAdapter(new SponsorAdapter(root.getContext(), items));

        return root;
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
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

    public class SponsorRow{
        ArrayList<SponsorItem> sponsors;
        int count;
        public SponsorRow(ArrayList<SponsorItem> sponsors){
            this.sponsors = sponsors;
            count = sponsors.size();
        }
        public SponsorItem get(int index){
            return sponsors.get(index);
        }
    }

    public class SponsorAdapter extends BaseAdapter {

        private ArrayList<Object> sponsorArray;
        private LayoutInflater inflater;
        private static final int TYPE_SPONSOR = 0;
        private static final int TYPE_HEADER = 1;

        public SponsorAdapter(Context context, ArrayList<Object> sponsorArray) {
            this.sponsorArray = sponsorArray;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return sponsorArray.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return sponsorArray.get(position);
        }

        @Override
        public int getViewTypeCount() {
            // TYPE_PERSON and TYPE_DIVIDER
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (getItem(position) instanceof SponsorRow) {
                return TYPE_SPONSOR;
            }

            return TYPE_HEADER;
        }

        @Override
        public boolean isEnabled(int position) {
            return (getItemViewType(position) == TYPE_SPONSOR);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                switch (type) {
                    case TYPE_SPONSOR:
                        convertView = inflater.inflate(R.layout.sponsor_list_item, parent, false);
                        break;
                    case TYPE_HEADER:
                        convertView = inflater.inflate(R.layout.section_header, parent, false);
                        break;
                }
            }

            switch(type) {
                case TYPE_SPONSOR:
                    final SponsorRow sponsorRow = (SponsorRow)getItem(position);
                    //initialize the relevant views
                    ImageView[] imageViews = new ImageView[5];
                    imageViews[0] = (ImageView) convertView.findViewById(R.id.sponsor_image_1);
                    imageViews[1] = (ImageView) convertView.findViewById(R.id.sponsor_image_2);
                    imageViews[2] = (ImageView) convertView.findViewById(R.id.sponsor_image_3);
                    imageViews[3] = (ImageView) convertView.findViewById(R.id.sponsor_image_4);
                    imageViews[4] = (ImageView) convertView.findViewById(R.id.sponsor_image_5);
                    //set the appropriate data
                    for (int i=0;i<5; i++){
                        if(i < sponsorRow.count){
                            AssetManager assetManager = getContext().getAssets();
                            InputStream is;
                            try {
                                is = assetManager.open(sponsorRow.get(i).logo+".png");
                                Bitmap bitmap = BitmapFactory.decodeStream(is);
                                imageViews[i].setImageBitmap(bitmap);
                                imageViews[i].setTag(sponsorRow.get(i).website);
                                is.close();
                                //set clicker
                                imageViews[i].setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        goToUrl((String)v.getTag());
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }


                    break;
                case TYPE_HEADER:
                    TextView headerTitle = (TextView) convertView.findViewById(R.id.section_header_title);
                    String titleString = (String)getItem(position);
                    headerTitle.setText(titleString);
                    break;
            }

            return convertView;
        }

        private void goToUrl (String url) {
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }
    }



}
