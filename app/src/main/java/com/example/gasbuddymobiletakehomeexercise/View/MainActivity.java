package com.example.gasbuddymobiletakehomeexercise.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gasbuddymobiletakehomeexercise.Model.Unsplash;
import com.example.gasbuddymobiletakehomeexercise.R;
import com.example.gasbuddymobiletakehomeexercise.ViewModel.UnsplashAdapter;
import com.example.gasbuddymobiletakehomeexercise.WebServices.ApiServices;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TextView tvMessage;
    private SimpleArcLoader pgBar;
    UnsplashAdapter mUnsplashAdapter;
    public static ArrayList<Unsplash> imageList;
    private String photographerName, date, location, likes, description, downloadLink, imageUrl, title;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etSearch;
    ImageView ivSearchImage, ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Photos");
        setSupportActionBar(toolbar);

        pgBar = findViewById(R.id.pgBar);
        tvMessage = findViewById(R.id.tvMessage);
        etSearch = findViewById(R.id.etSearch);
        ivSearchImage = findViewById(R.id.ivSearchImage);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setVisibility(View.GONE);
        etSearch.setVisibility(View.GONE);
        pgBar.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        showSearchBar();
        hideSearchBar();
        tvMessage.setText("Loading...");
        searchImages();
        init();
        refreshList();

    }

    private void refreshList() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  mUnsplashAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });
    }

    private void init() {
        pgBar.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        Call<List<Unsplash>> call = ApiServices.iapiAccessKey().getUnsplashList();
        call.enqueue(new Callback<List<Unsplash>>() {
            @Override
            public void onResponse(Call<List<Unsplash>> call, Response<List<Unsplash>> response) {
                imageList = (ArrayList<Unsplash>) response.body();
                Log.d(TAG, "RESPONSE CODE " + response.code());
                Log.d(TAG, "RESPONSE RAW " + response.raw());
                Log.d(TAG, "onResponse message : " + response.message());


                for (int i = 0; i < imageList.size(); i++) {
                    photographerName = imageList.get(i).getUser().getName();
                    date = imageList.get(i).getUpdatedAt();
                    location = imageList.get(i).getUser().getLocation();
                    likes = String.valueOf(imageList.get(i).getLikes());

                    imageUrl = imageList.get(i).getUrls().getFull();

                }
                Log.d(TAG, "FOR LOOP  " + "\n"
                        + photographerName + "\n"
                        + date + "\n"
                        + location + "\n"
                        + likes + "\n"
                        + imageUrl + "\n");
                fetchUnsplashData(response.body());
                if (response.isSuccessful()) {

                    mUnsplashAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                            int position = viewHolder.getAdapterPosition();
                            Log.d(TAG, "POSITION=====================> " + position);
                            title = imageList.get(position).getUser().getName();
                            photographerName = imageList.get(position).getUser().getFirstName() + " " + imageList.get(position).getUser().getLastName();
                            date = imageList.get(position).getUpdatedAt();
                            location = imageList.get(position).getUser().getLocation();
                            likes = String.valueOf(imageList.get(position).getLikes());
                            description = imageList.get(position).getAltDescription();
                            downloadLink = imageList.get(position).getLinks().getDownload();
                            imageUrl = imageList.get(position).getUrls().getFull();


                            Log.d(TAG, "POSITION :" + position);
                            Log.d(TAG, "OnRESPONSE :  " + "\n"
                                    + photographerName + "\n"
                                    + date + "\n"
                                    + location + "\n"
                                    + likes + "\n"
                                    + imageUrl + "\n");

                            Intent toImageDetail = new Intent(MainActivity.this, ImageDetail.class);
                            toImageDetail.putExtra("title", title);
                            toImageDetail.putExtra("photo", photographerName);
                            toImageDetail.putExtra("date", date);
                            toImageDetail.putExtra("locations", location);
                            toImageDetail.putExtra("likes", likes);
                            toImageDetail.putExtra("description", description);
                            toImageDetail.putExtra("downloadImage", downloadLink);
                            toImageDetail.putExtra("image", imageUrl);
                            startActivity(toImageDetail);

                        }


                    });

                    pgBar.setVisibility(View.GONE);
                    tvMessage.setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "IF NULL : " + response.errorBody());
                    Log.d(TAG, "onResponse: " + response.message());
                    pgBar.setVisibility(View.GONE);
                    tvMessage.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<List<Unsplash>> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "FAILURE :  " + t.getMessage());
                pgBar.setVisibility(View.GONE);
                tvMessage.setVisibility(View.GONE);
            }
        });
    }

    public void fetchUnsplashData(List<Unsplash> fetchList) {
        Log.d(TAG, "NOTIFY DATA " + "CHECK POINT 1");
        final ArrayList<Unsplash> list = (ArrayList<Unsplash>) fetchList;
        mUnsplashAdapter = new UnsplashAdapter(list, getApplicationContext());

        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mUnsplashAdapter);

    }

    private void showSearchBar() {
        ivSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBack.setVisibility(View.VISIBLE);
                etSearch.setVisibility(View.VISIBLE);
                ivSearchImage.setVisibility(View.GONE);
            }
        });
    }

    private void hideSearchBar() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                ivBack.setVisibility(View.GONE);
                etSearch.setVisibility(View.GONE);
                ivSearchImage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void searchImages() {
        Log.d(TAG, "NOTIFY DATA " + "CHECK POINT 2");
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "NOTIFY DATA " + "CHECK POINT 3");
                mUnsplashAdapter.getFilter().filter(s);
                //  mUnsplashAdapter.notifyDataSetChanged();
                Log.d(TAG, "ITEM ID:  " + mUnsplashAdapter.getItemId(count));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
