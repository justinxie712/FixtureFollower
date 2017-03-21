package com.example.jxie.fixturefollower20;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 3/18/2017.
 */

public class Parser extends AppCompatActivity {


    public Parser() {
    }

    public void Parse(int thisID, final LatLng latlng, final CallBack callback) {
        final RequestQueue requestQueue = Volley.newRequestQueue(AppController.getInstance());
        String teamid = Integer.toString(thisID);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.football-data.org/v1/teams/" + teamid + "/fixtures?timeFrame=n21",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Fixture> fixtures;
                        System.out.println("this is Response!!");
                        System.out.println(response);
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            fixtures = ParseHelper(responseObject, latlng);
                            callback.OnSuccess(fixtures);
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                            callback.OnFail(e.toString());
                        }
                        requestQueue.stop();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("this is Error!!");
                        System.out.println(error);
                        requestQueue.stop();
                    }
                }
        ){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-Auth-Token","c3bde62900244ca8ab734f01650d1f95");
                params.put("X-Response-Control","full");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }



    public ArrayList<Fixture> ParseHelper(JSONObject thisObject, LatLng latLng){
        ArrayList<Fixture> matches = new ArrayList<>();
        try {
            JSONArray fixtures = (JSONArray) thisObject.get("fixtures");
            System.out.println(fixtures);

            for (int i = 0 ; i < fixtures.length() ; i++){
                JSONObject obj = fixtures.getJSONObject(i);
                String hometeamName = obj.get("homeTeamName").toString();
                String awayteamName = obj.get("awayTeamName").toString();
                String matchday = obj.get("matchday").toString();
                String date = obj.get("date").toString();
//                System.out.println(hometeamName);
//                System.out.println(awayteamName);
//                System.out.println(matchday);
//                System.out.println(date);
                Fixture fixture = new Fixture(hometeamName,awayteamName,latLng,date,matchday);
                System.out.println(fixture);
                matches.add(fixture);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("In Parser helper");
        System.out.println(matches);
        return matches;
    }

}
