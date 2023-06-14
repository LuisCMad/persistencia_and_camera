package com.example.felipe_ochoa.data.network;

import com.example.felipe_ochoa.data.models.University;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {
    @GET("search")
    Call<List<University>> getUniversitiesByCountry(@Query("country") String country);
}
