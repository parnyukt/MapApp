package com.test.tania.mapapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.tania.mapapp.api.IMapApi;
import com.test.tania.mapapp.api.dto.Place;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowCloseListener {

    @Inject
    IMapApi mapApi;

    private GoogleMap mMap;
    private Snackbar placeDetailsPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        placeDetailsPanel = Snackbar.make(findViewById(R.id.layout),
                "", Snackbar.LENGTH_INDEFINITE);

        MapApp.getAppComponent().inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        addMarkersToMap();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);
    }

    public void addMarkersToMap() {
        final List<Place> placesList = new ArrayList<>();
        mapApi.placesList().enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.body() == null) return;

                placesList.addAll(response.body());
                showMarkersToMap(placesList);
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server connection error. Try again later.", Toast.LENGTH_SHORT).show();
                Log.e(getClass().getSimpleName(), t.getMessage());
            }
        });
    }

    private void showMarkersToMap(List<Place> places) {
        for (Place place : places) {
            addMarker(place);
        }
    }

    private void addMarker(Place place) {
        LatLng marker = new LatLng(place.lat, place.lng);
        mMap.addMarker(new MarkerOptions().position(marker).title(place.title).snippet(place.description));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        placeDetailsPanel.setText(marker.getSnippet());
        placeDetailsPanel.show();

        return false;
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        placeDetailsPanel.dismiss();
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mContents;

        CustomInfoWindowAdapter() {
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                titleUi.setText(title);
            } else {
                titleUi.setText("");
            }
        }
    }
}
