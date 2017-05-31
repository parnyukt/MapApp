package com.test.tania.mapapp.api;

import com.test.tania.mapapp.api.dto.Place;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IMapApi {
    @GET("bPvorlFkwO")
    Call<List<Place>> placesList();
}




