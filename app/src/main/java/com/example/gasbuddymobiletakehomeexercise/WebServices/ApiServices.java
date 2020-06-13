package com.example.gasbuddymobiletakehomeexercise.WebServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {

    public static final String BASE_URL = "https://api.unsplash.com/";

    public static Retrofit getUnsplashImages(){
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static IAPIAccessKey iapiAccessKey(){
        return getUnsplashImages().create(IAPIAccessKey.class);
    }


}
