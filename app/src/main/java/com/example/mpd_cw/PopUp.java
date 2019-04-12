/*<!--// Name                 Andrew MacFarlane-->
        <!--// Student ID           S1511223-->
        <!--// Programme of Study   Computer Games(Software Development)-->
        <!--//-->*/
package com.example.mpd_cw;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopUp extends Activity {

    private TextView tvDate;
    private TextView tvTime;
    private TextView tvLoc;
    private TextView tvLat;
    private TextView tvLong;
    private TextView tvDepth;
    private TextView tvMag;

    private String dateString;
    private String timeString;
    private String locationString;
    private String latString;
    private String longString;
    private String depthString;
    private String magString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.8), (int)(height*.6));
        GetExtras();
        GetViews();
        SetTexts();
    }

    private void GetExtras()
    {
        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            dateString = (String)extras.get("date");

            timeString = (String)extras.get("time");

            locationString = (String)extras.get("location");

            latString = (String)extras.get("lat");

            longString = (String)extras.get("long");

            depthString = (String)extras.get("depth");

            magString = (String)extras.get("mag");


        }
    }

    private void GetViews(){
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvLoc = findViewById(R.id.tvLoc);
        tvLat = findViewById(R.id.tvLat);
        tvLong = (TextView)findViewById(R.id.tvLong);
        tvDepth = findViewById(R.id.tvDepth);
        tvMag = findViewById(R.id.tvMag);

    }

    private void SetTexts()
    {
        // Log.e("Window", dateString);
        tvDate.setText("Date: " + dateString);
        tvTime.setText("Time: " + timeString);
        tvLoc.setText(locationString);
        tvLat.setText("Latitude: " + latString);
        tvLong.setText("Longitude: " + longString );
        tvDepth.setText("Depth: " + depthString + " km");
        tvMag.setText("Magnitude: " + magString);
    }


}
