package com.mrsaad.hackwestern;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class ScheduleFragment extends Fragment {

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set view layout
        final View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        //initialize items
        ArrayList<String> days = new ArrayList<String>(Arrays.asList("November 27, 2015", "November 28, 2015", "November 29, 2015"));
        ArrayList<Object> items = new ArrayList<Object>();

        //add stuff to items from JSON
        int tag=0;
        try{
            JSONObject obj = new JSONObject(loadJSONFromAsset("schedule_info.json"));
            for(String day: days){
                items.add(day);
                JSONArray arr = obj.getJSONArray(day);
                Log.v("DEBUG", arr.getJSONObject(0).toString());
                for(int i=0; i< arr.length(); i++){
                    JSONObject currObj = arr.getJSONObject(i);
                    items.add(new ScheduleItem(currObj.getString("title"), currObj.getString("time"), currObj.getString("info"),
                            currObj.getString("location"), tag++));
                }
            }
        }catch(Exception e){e.printStackTrace();}

        //initialize list view
        ListView listView = (ListView) root.findViewById(R.id.schedule_list);
        listView.setAdapter(new ScheduleAdapter(root.getContext(), items));

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

    public class ScheduleAdapter extends BaseAdapter {

        private ArrayList<Object> scheduleArray;
        private LayoutInflater inflater;
        private static final int TYPE_SCHEDULE = 0;
        private static final int TYPE_HEADER = 1;

        public ScheduleAdapter(Context context, ArrayList<Object> scheduleArray) {
            this.scheduleArray = scheduleArray;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return scheduleArray.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return scheduleArray.get(position);
        }

        @Override
        public int getViewTypeCount() {
            // TYPE_PERSON and TYPE_DIVIDER
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (getItem(position) instanceof ScheduleItem) {
                return TYPE_SCHEDULE;
            }

            return TYPE_HEADER;
        }

        @Override
        public boolean isEnabled(int position) {
            return (getItemViewType(position) == TYPE_SCHEDULE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                switch (type) {
                    case TYPE_SCHEDULE:
                        convertView = inflater.inflate(R.layout.schedule_list_item_2, parent, false);
                        break;
                    case TYPE_HEADER:
                        convertView = inflater.inflate(R.layout.section_header, parent, false);
                        break;
                }
            }

            switch(type) {
                case TYPE_SCHEDULE:
                    ScheduleItem scheduleItem = (ScheduleItem)getItem(position);
                    // Lookup view for data population
                    TextView titleView = (TextView) convertView.findViewById(R.id.schedule_list_title);
                    TextView dateView = (TextView) convertView.findViewById(R.id.schedule_list_date);
                    TextView locationView = (TextView) convertView.findViewById(R.id.schedule_list_location);
                    TextView contentView = (TextView) convertView.findViewById(R.id.schedule_list_content);
                    // Populate the data into the template view using the data object
                    titleView.setText(scheduleItem.title);
                    dateView.setText(scheduleItem.date);
                    locationView.setText(scheduleItem.location);
                    contentView.setText(scheduleItem.content);
                    //set background color based on tag
//                    if(scheduleItem.tag%2==0){convertView.setBackgroundColor(Color.parseColor("#EDE7F6"));}
//                    else{convertView.setBackgroundColor(Color.parseColor("#D1C4E9"));}
                    break;
                case TYPE_HEADER:
                    TextView headerTitle = (TextView) convertView.findViewById(R.id.section_header_title);
                    String titleString = (String)getItem(position);
                    headerTitle.setText(titleString);
                    break;
            }

            return convertView;
        }
    }


}
