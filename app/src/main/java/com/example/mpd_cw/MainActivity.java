
// Name                 Andrew MacFarlane
// Student ID           S1511223
// Programme of Study   Computer Games(Software Development)
//

// Update the package name to include your Student Identifier
package com.example.mpd_cw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.view.Menu;

import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class MainActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener, RecyclerAdapter.CardListener {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<Earthquake> quakes = new ArrayList<>();
    List<String> testList;
    ArrayList<Earthquake> sortedList;
    EditText searchBar;
    int textLength = 0;
    boolean isClicked = false;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ProcessInBackground().execute();
        //RecyclerInit();
        //SearchBarInit();
        SortSpinnerInit();
        //OrderSpinnerInit();


    }


    public void SortSpinnerInit()
    {
        Spinner sortSpinner = (Spinner) findViewById(R.id.SortSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.SortOptions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(myAdapter);
        sortSpinner.setOnItemSelectedListener(this);
    }



    public void RecyclerInit()
    {







        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adapter = new RecyclerAdapter(quakes, MainActivity.this, this);

        recyclerView.setAdapter(adapter);




       // searchBar = (EditText) findViewById(R.id.search_bar);







    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }





    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        Sort(parent.getSelectedItem().toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void OnCardClick(int pos) {
        Log.d("andrew", "clicked");
        Intent intent = new Intent(MainActivity.this, PopUp.class);

        intent.putExtra("location", quakes.get(pos).GetLoc());
        intent.putExtra("date", quakes.get(pos).GetFullDate());
        intent.putExtra("time", quakes.get(pos).GetFullTime());
        intent.putExtra("depth", Float.toString(quakes.get(pos).GetDepth()));
        intent.putExtra("mag", Float.toString(quakes.get(pos).GetMag()));
        intent.putExtra("lat", Float.toString(quakes.get(pos).GetLat()));
        intent.putExtra("long", Float.toString(quakes.get(pos).GetLong()));

        startActivity(intent);

    }


    void Sort(String sortBy) {


        //if(mySpinner.getSelectedItem().toString() == "Ascending"){

        switch (sortBy) {
            case "Sort By":
                break;
            case "Depth Low- Hi":
                Collections.sort(quakes, new DepthUp());
                break;case "Depth Hi-Low":
                Collections.sort(quakes, new DepthDown());
                break;
            case "Location A-Z":
                Collections.sort(quakes, new Comparator<Earthquake>() {
                    @Override
                    public int compare(Earthquake o1, Earthquake o2) {
                        return o1.GetLoc().compareTo(o2.GetLoc());
                    }
                });
                adapter.notifyDataSetChanged();
                break;case "Location Z-A":
                Collections.sort(quakes, new Comparator<Earthquake>() {
                    @Override
                    public int compare(Earthquake o1, Earthquake o2) {
                        return o2.GetLoc().compareTo(o1.GetLoc());
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case "Magnitude Low-Hi":
                Collections.sort(quakes, new MagUp());
                break;
                case "Magnitude Hi-Low":
                Collections.sort(quakes, new MagDown());
                break;
            case "North-South ":
                Collections.sort(quakes, new SortNorthMost());
                break;
                case "South-North ":
                Collections.sort(quakes, new South());
                break;
            case "East-West":
                Collections.sort(quakes, new East());
                break;
                case "West-East":
                Collections.sort(quakes, new West());
                break;
            default:

                //throw new IllegalArgumentException("Input Not Found: " + sortBy);

        //}

    }


         /*Collections.sort(quakes, new Comparator<Earthquake>() {
            @Override
            public int compare(Earthquake o1, Earthquake o2) {
                return o1.GetLoc().compareTo(o2.GetLoc());
            }
        });
        adapter.notifyDataSetChanged();*/
    }


    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {


        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setMessage("Loading XML File...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers)
        {
            try
            {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem= false;

                int eventType = xpp.getEventType();

                while ( eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }

                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                descriptions.add(xpp.nextText());


                            }
                        }


                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }

            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception e)
        {



            for (int i = 0; i < descriptions.size(); i++)
            {
                String[] sections  = descriptions.get(i).split(";");
                StringSplitter(sections);
            }
            Log.d("andrew", "quakes" + quakes.size());
            RecyclerInit();
            progressDialog.dismiss();
        }
    }

    public void StringSplitter(String[] fullDesc)
    {
        String day;
        String date;
        String month;
        String year;
        String hour;
        String  minute;
        String second;
        String location;
        String lat;
        String longitude;
        String depth;
        String mag;

        day = fullDesc[0].substring(18,21);

        date = fullDesc[0].substring(23,25);

        month = fullDesc[0].substring(26,30);

        year = fullDesc[0].substring(30,34);
        hour = fullDesc[0].substring(35,37);
        minute = fullDesc[0].substring(38,40);
        second = fullDesc[0].substring(41,43);
        location = fullDesc[1].split(":")[1];
        location = location.trim();
        lat = fullDesc[2].substring(11,17);
        longitude = fullDesc[2].substring(18,24);
        longitude = longitude.trim();
        depth = fullDesc[3].substring(7,10);
        depth = depth.trim();
        mag = fullDesc[4].substring(12,16);
        mag = mag.trim();


        //Log.d("andrew" , "" + location);
        Earthquake e = new Earthquake(day, Float.parseFloat(date), month,
                (Float.parseFloat(year)), Float.parseFloat(hour), Float.parseFloat(minute),
                Float.parseFloat(second), location, Float.parseFloat(lat),
                Float.parseFloat(longitude), Float.parseFloat(depth), Float.parseFloat(mag));
        quakes.add(e);
    }


    @Override
    public void onClick(View v)
    {



    }




    class SortNorthMost implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aLat = a.lat * 1000;
            float bLat = b.lat * 1000;

            return (int) bLat - (int) aLat;
        }
    }

    class South implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aLat = a.lat * 1000;
            float bLat = b.lat * 1000;

            return (int) aLat - (int) bLat;
        }
    }

    class East implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aLong = a.longitude * 1000;
            float bLong = b.longitude * 1000;

            return (int) bLong - (int) aLong;
        }
    }

    class West implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aLong = a.longitude * 1000;
            float bLong = b.longitude * 1000;

            return (int) aLong - (int) bLong;
        }
    }



    class DepthUp implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aDepth = a.depth;
            float bDepth = b.depth;

            return (int) aDepth - (int) bDepth;
        }
    }

    class DepthDown implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aDepth = a.depth;
            float bDepth = b.depth;

            return (int) bDepth - (int) aDepth;
        }
    }

    class MagUp implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aMag = a.mag * 10;
            float bMag = b.mag * 10;

            return (int) aMag - (int) bMag;
        }
    }

    class MagDown implements Comparator<Earthquake> {
        public int compare(Earthquake a, Earthquake b) {
            float aMag = a.mag * 10;
            float bMag = b.mag * 10;

            return (int) bMag - (int) aMag;
        }
    }


}