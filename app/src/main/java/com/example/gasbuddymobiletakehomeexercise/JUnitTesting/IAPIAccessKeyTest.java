package com.example.gasbuddymobiletakehomeexercise.JUnitTesting;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gasbuddymobiletakehomeexercise.Model.Unsplash;
import com.example.gasbuddymobiletakehomeexercise.WebServices.ApiServices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class IAPIAccessKeyTest {
    private static final String TAG = "IAPIAccessKeyTest";
    private List<Unsplash> list;
    private String actual;
    private  int responseCode;

    @Test
    public void getUnsplashList() {

        Call<List<Unsplash>> call = ApiServices.iapiAccessKey().getUnsplashList();
        call.enqueue(new Callback<List<Unsplash>>() {
            @Override
            public void onResponse(Call<List<Unsplash>> call, Response<List<Unsplash>> response) {
                 list = response.body();

                if(response.body() != null){
                   actual  = response.body().toString();
                   responseCode = response.code();
                }

//                if (response.isSuccessful()) {
//
//                    Log.d(TAG, "onResponse: " + response.body());
////                    int position = viewHolder.getAdapterPosition();
////                    photographerName = mArrayList.get(position).getUser().getName();
////                    date = mArrayList.get(position).getUpdatedAt();
////                    location = mArrayList.get(position).getUser().getLocation();
////                    likes = String.valueOf(mArrayList.get(position).getLikes());
////                    imageUrl = mArrayList.get(position).getUrls().getFull();
//
//
////                    Log.d(TAG, "onResponse: " + photographerName + "\n"
////                            + date + "\n"
////                            + location + "\n"
////                            + likes + "\n"
////                            + imageUrl + "\n");
//
//                }
//                else{
//                    Log.d(TAG, "IF NULL : " + response.errorBody());
//                    Log.d(TAG, "onResponse: " + response.message());
//                }
               String expected = "200 OK";
                assertEquals("Match Response : ",expected,actual );
                assertEquals("Response code: " + 200, responseCode);

            }

            @Override
            public void onFailure(Call<List<Unsplash>> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "FAILURE :  " + t.getMessage());
            }
        });


    }

}