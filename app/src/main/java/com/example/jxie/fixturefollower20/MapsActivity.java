package com.example.jxie.fixturefollower20;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner mSpinner;
    int id = HomeActivity.id;
    String matchday = "";
    String selection = HomeActivity.selection;
    LatLng home_location = null;
    Map<String, LatLng> cities = new HashMap<>();
    ArrayList<Fixture> globalFixtures = new ArrayList<>();
    Team globalTeam = null;

    LatLng hull = new LatLng(53.7465,-0.368009);
    LatLng villa = new LatLng(52.5092,-1.88508);
    LatLng burnley = new LatLng(53.7888,-2.23018);
    LatLng chelsea = new LatLng(51.4816, -0.191034);
    LatLng bournemouth = new LatLng(50.7352, -1.83839);
    LatLng arsenal = new LatLng(51.5549, -0.108436);
    LatLng cp = new LatLng(51.3983, -0.085455);
    LatLng everton = new LatLng(53.4387, -2.96619);
    LatLng leiceister = new LatLng(52.6203, -1.14217);
    LatLng liverpool = new LatLng(53.4308, -2.96096);
    LatLng mancity = new LatLng(53.483, -2.20024);
    LatLng manu = new LatLng(53.4631, -2.29139);
    LatLng newcastle = new LatLng(54.9756,-1.62179);
    LatLng norwich = new LatLng(52.6221,1.30912);
    LatLng south = new LatLng(50.9058, -1.39114);
    LatLng stoke = new LatLng(52.9884, -2.17542);
    LatLng sunder = new LatLng(54.9146, -1.38837);
    LatLng swansea = new LatLng(51.6428, -3.93473);
    LatLng tottenham = new LatLng(50.7352, -1.83839);
    LatLng watford = new LatLng(51.6498, -0.401569);
    LatLng westbrom = new LatLng(52.509, -1.96418);
    LatLng westham = new LatLng(51.5383, -0.016587);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intializelocations();

        Parser parser = new Parser();

        //Second Parser function
        parser.ParseTeam(id, new CallbackTeam() {
            @Override
            public void OnSuccessTeam(Team team) {
                globalTeam = team;
                System.out.println(globalTeam);
            }

            @Override
            public void OnFail(String msg) {
                System.out.println(msg);
            }
        });

        home_location = cities.get(selection);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mSpinner = (Spinner)findViewById(R.id.spinner);
        mSpinner.setEnabled(true);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.matchdays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        // Spinner selector for matchdays
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long cid)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equals("All matchdays"))
                {
                    matchday = "0";
                }
                if(selectedItem.equals("Matchday 1"))
                {
                    matchday = "1";
                }
                if(selectedItem.equals("Matchday 2"))
                {
                    matchday = "2";
                }

                if(selectedItem.equals("Matchday 3"))
                {
                    matchday = "3";
                }

                if(selectedItem.equals("Matchday 4"))
                {
                    matchday = "4";
                }
                if(selectedItem.equals("Matchday 5"))
                {
                    matchday = "5";
                }
                if(selectedItem.equals("Matchday 6"))
                {
                    matchday = "6";
                }
                if(selectedItem.equals("Matchday 7"))
                {
                    matchday = "7";
                }
                if(selectedItem.equals("Matchday 8"))
                {
                    matchday = "8";
                }
                if(selectedItem.equals("Matchday 9"))
                {
                    matchday = "9";
                }
                if(selectedItem.equals("Matchday 10"))
                {
                    matchday = "10";
                }

                // If all matchdays option is selected then plot all the fixures in the season
                if(matchday.equals("0")) {
                    for(Fixture fixture: globalFixtures ){
                        if(cities.get(fixture.homeTeamName)!= null) {
                            LatLng pos = cities.get(fixture.homeTeamName);
                            mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + " VS " + fixture.awayTeamName)
                                    .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(home_location));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(home_location, 6));
                        }
                    }
                }
                else {
                    // If any other option other than all matchdays is selected then only plot the specific fixture for the matchday
                    for (Fixture fixture : globalFixtures) {
                        if (fixture.matchday.equals(matchday)) {
                            mMap.clear();
                            if (cities.get(fixture.homeTeamName) != null) {
                                LatLng pos = cities.get(fixture.homeTeamName);
                                mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + " VS " + fixture.awayTeamName)
                                        .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(home_location));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(home_location, 6));
                            }
                        }
                    }
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }

    // Add String, Latlong combination into the map for different cities
    private void intializelocations() {
        cities.put("Aston Villa FC",villa);
        cities.put("Arsenal FC",arsenal);
        cities.put("Burnley FC",burnley);
        cities.put("AFC Bournemouth",bournemouth);
        cities.put("Chelsea FC",chelsea);
        cities.put("Crystal Palace FC",cp);
        cities.put("Everton FC", everton);
        cities.put("Hull City FC",hull);
        cities.put("Leicester City FC",leiceister);
        cities.put("Liverpool FC",liverpool);
        cities.put("Manchester City",mancity);
        cities.put("Manchester United FC",manu);
        cities.put("Newcastle United FC",newcastle);
        cities.put("Norwich City FC",norwich);
        cities.put("Southampton FC",south);
        cities.put("Stoke City FC",stoke);
        cities.put("Sunderland AFC",sunder);
        cities.put("Swansea City FC",swansea);
        cities.put("Tottenham Hotspur FC",tottenham);
        cities.put("Watford FC",watford);
        cities.put("West Bromwich Albion FC",westbrom);
        cities.put("West Ham United FC",westham);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final Parser parser = new Parser();
        mMap = googleMap;

        parser.Parse(id, new CallBack() {
            @Override
            public void OnSuccess(ArrayList<Fixture> fixtures) {
                globalFixtures = fixtures;

                for(Fixture fixture: fixtures ){
                    if(cities.get(fixture.homeTeamName)!= null) {
                        LatLng pos = cities.get(fixture.homeTeamName);
                        // If match has been already played and a result exists
                        if(!fixture.goalsHome.equals("null")){
                            System.out.println(selection);
                            System.out.println(fixture.homeTeamName);
                            //home game
                            if(fixture.homeTeamName.equals(selection)) {
                                System.out.println(selection);
                                System.out.println(fixture.homeTeamName);
                                //home win
                                if(Integer.parseInt(fixture.goalsHome)>Integer.parseInt(fixture.goalsAway)) {
                                    mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + fixture.goalsHome + " " + fixture.awayTeamName + fixture.goalsAway)
                                            .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                }
                                //home draw
                                if(Integer.parseInt(fixture.goalsHome)==Integer.parseInt(fixture.goalsAway)) {
                                    mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + fixture.goalsHome + " " + fixture.awayTeamName + fixture.goalsAway)
                                            .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                }
                                //home loss
                                if(Integer.parseInt(fixture.goalsHome)< Integer.parseInt(fixture.goalsAway)) {
                                    mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + fixture.goalsHome + " " + fixture.awayTeamName + fixture.goalsAway)
                                            .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                            }
                            if(fixture.awayTeamName.equals(selection)) {
                                //away win
                                if(Integer.parseInt(fixture.goalsHome)< Integer.parseInt(fixture.goalsAway)) {
                                    mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + fixture.goalsHome + " " + fixture.awayTeamName + fixture.goalsAway)
                                            .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                }
                                //away draw
                                if(Integer.parseInt(fixture.goalsHome)==Integer.parseInt(fixture.goalsAway)) {
                                    mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + fixture.goalsHome + " " + fixture.awayTeamName + fixture.goalsAway)
                                            .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                }
                                //away loss
                                if(Integer.parseInt(fixture.goalsHome)> Integer.parseInt(fixture.goalsAway)) {
                                    mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + fixture.goalsHome + " " + fixture.awayTeamName + fixture.goalsAway)
                                            .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                            }

                        }
                        else {
                            mMap.addMarker(new MarkerOptions().position(pos).title(fixture.homeTeamName + " VS " + fixture.awayTeamName)
                                    .snippet("Matchday: " + fixture.matchday).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(home_location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(home_location, 6));
                    }
                }
            }
            @Override
            public void OnFail(String msg) {
                System.out.println(msg);

            }

        });

        parser.ParseTeam(id, new CallbackTeam() {
            @Override
            public void OnSuccessTeam(Team team) {
                System.out.println(team);
            }

            @Override
            public void OnFail(String msg) {
                System.out.println(msg);

            }
        });

    }
}
