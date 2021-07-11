package com.example.rssnews.api_country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    static final String BASE_URL = "https://corona.lmao.ninja/v2/";
    @GET("countries")
    Call<List<CountryData>> getCountryData();
}
