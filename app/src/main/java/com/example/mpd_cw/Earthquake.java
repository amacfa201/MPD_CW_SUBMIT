package com.example.mpd_cw;
/*<!--// Name                 Andrew MacFarlane-->
<!--// Student ID           S1511223-->
<!--// Programme of Study   Computer Games(Software Development)-->
<!--//-->*/
class Earthquake {


    /*String totalOutput;
    String dayWeek;
    int date; //26011998
    int time; // 22:31:33 // 223133
    String location;
    float lat;
    float longitude;
    int depth;
    float magnitude;*/

    String day;
    float date;
    String month;
    float year;
    float hour;
    float minute;
    float second;
    String location;
    float lat;
    float longitude;
    float depth;
    float mag;

    String head = "Test";
    String Desc = "Test";

    Earthquake(String _day,
            float _date,
            String _month,
            float _year,
            float _hour,
            float _minute,
            float _second,
            String _location,
            float _lat,
            float _longitude,
            float _depth,
            float _mag){

        day = _day;
        date = _date;
        month = _month;
        year = _year;
        hour = _hour;
        minute = _minute;
        second = _second;
        location = _location;
        lat = _lat;
        longitude = _longitude;
        depth = _depth;
        mag = _mag;

    }


    //Setters
    /*public void SetTotalOutput(String _totalOutput){this.totalOutput = _totalOutput;}

    public void SetDayWeek(String _dayWeek){this.dayWeek = _dayWeek;}
    public void SetDate(int _date){this.date = _date;}
    public void SetTime(int _time){this.time = _time;}
    public void SetLoc(String _location){this.location = _location;}
    public void SetLat(float _lat){this.lat = _lat;}
    public void SetLong(float _long){this.longitude = _long;}
    public void SetMag(float _magnitude){this.magnitude = _magnitude;}*/



    //Getters


    public String GetDay(){return day;}
    public float GetDate(){return date;}
    public String GetMonth(){return day;}
    public float GetYear(){return year;}
    public float GetHour(){return hour;}
    public float GetMinute(){return minute;}
    public float GetSecond(){return second;}
    public String GetLoc(){return location;}
    public float GetLat(){return lat;}
    public float GetLong(){return longitude;}
    public float GetDepth(){return depth;}
    public float GetMag(){return mag;}
    public String getHead(){return head;}
    public String getDesc(){return Desc;}
    //public String GetFullDate(){return  day + " " + String.format("%d",  Float.toString(date)) + " " + month + String.format(java.util.Locale.US,"%.0f",  Float.toString(year));}
    public String GetFullDate(){return  day + " " + Float.toString(date) + " " + month + Float.toString(year);}
    //public String GetFullTime(){return String.format("%.0f",  Float.toString(hour)) + ":" +  String.format("%.0f",  Float.toString(minute)) + ":"+ String.format("%.0f",  Float.toString(second));}
    public String GetFullTime(){return Float.toString(hour) + ":" +  Float.toString(minute) + ":"+ Float.toString(second);}



}
