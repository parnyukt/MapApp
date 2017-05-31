package com.test.tania.mapapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.tania.mapapp.api.IMapApi;
import com.test.tania.mapapp.api.dto.Place;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Inject
    IMapApi mapApi;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapApp.getAppComponent().inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        addMarkersToMap();

    }

    public void addMarkersToMap() {
        final List<Place> placesList = new ArrayList<>();
        mapApi.placesList().enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.body() == null) return;

                placesList.addAll(response.body());
                showMarkers(placesList);
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.i(getClass().getSimpleName(), "issue");
            }
        });
    }

    private void showMarkers(List<Place> places) {
        for (Place place : places) {
            addMarker(place);
        }
    }

    private void addMarker(Place place) {
        LatLng sydney = new LatLng(place.lat, place.lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title(place.title));
    }

}
