package com.example.gasbuddymobiletakehomeexercise.WebServices;

import com.example.gasbuddymobiletakehomeexercise.Model.Unsplash;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAPIAccessKey {
    @GET("photos?=1&per_page=30&client_id=OMM6sK0rUeJVGU06WhUQKkKREaMNjfQkNHRi_iLUivI")
    Call<List<Unsplash>> getUnsplashList();
}
