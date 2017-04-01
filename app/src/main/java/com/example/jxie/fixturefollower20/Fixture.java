package com.example.jxie.fixturefollower20;

import android.graphics.drawable.Icon;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jason on 3/18/2017.
 */

public class Fixture {
    String homeTeamName;
    String awayTeamName;
    String date;
    String matchday;
    String goalsHome;
    String goalsAway;

    public Fixture(String htm, String atm, String date, String matchday,String goalsHome, String goalsAway) {
        this.homeTeamName = htm;
        this.awayTeamName = atm;
        this.date = date;
        this.matchday = matchday;
        this.goalsHome = goalsHome;
        this.goalsAway = goalsAway;
    }
}
