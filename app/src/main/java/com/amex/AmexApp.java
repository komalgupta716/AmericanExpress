package com.amex;

import android.app.Application;

import java.util.ArrayList;


public class AmexApp extends Application {
    public ArrayList<Venue> mVenues;

    @Override
    public void onCreate() {
        super.onCreate();
        mVenues = new ArrayList<>();
        mVenues.add(new Venue("Shopper Stop- D227 Anand Vihar , New Delhi-110033",
                "10% cashback on shopping above Rs-4999",
                "Anjali Jain", 28.6523086, 77.3152082,0));
        mVenues.add(new Venue("McDonalds - 342, Main Market, Dwarka sector-12 , New Delhi -110021",
                "", "NSIT", 28.608052, 77.036187,1));
        mVenues.add(new Venue("Dominos- Plot No 54, East Punjabi Bagh , New Delhi 110026",
                "20% cashback upto Rs-amex_icon100",
                "Mayank",
                28.667589, 77.141083,2));
        mVenues.add(new Venue("Stanmax, 1st Floor, Unity Mall , Shalimar Bagh , New Delhi-110023",
                "",
                "Simran Bedi", 28.7075
                , 77.1677,3));
        mVenues.add(new Venue("Taco Bell  DDA main market, Hauz Khas New Delhi-110016",
                "5% cashback upto Rs-200",
                " Aditya Gupta", 28.538144, 77.222046,4));

    }
}
