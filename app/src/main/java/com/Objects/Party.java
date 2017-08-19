package com.Objects;

import android.icu.text.MessagePattern;

/**
 * Created by eliott on 15/08/2017.
 */

public class Party {

    public int year, month, day, hour, minute;

    public String organizer;
    public String location;
    public String id;

    public Party(){

    }

    public Party(int year, int month, int day, int hour, int minute, String organizer, String location, String id){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.organizer = organizer;
        this.location = location;
        this.id = id;
    }

}
