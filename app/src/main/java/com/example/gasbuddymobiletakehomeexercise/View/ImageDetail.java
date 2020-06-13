package com.example.gasbuddymobiletakehomeexercise.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gasbuddymobiletakehomeexercise.Model.Unsplash;
import com.example.gasbuddymobiletakehomeexercise.R;
import com.leo.simplearcloader.SimpleArcLoader;


public class ImageDetail extends AppCompatActivity {
    private static final String TAG = "ImageDetail";
    ImageView ivImage, ivDownload, ivFullScreen;
    TextView tvPhotographerName, tvDate, tvLocation, tvLikes, tvDescription;
    Toolbar toolbar;
    private TextView tvMessage;
    private SimpleArcLoader pgBar;
    private String photographerName, date, location, likes, description, downloadLink, imageUrl, title;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Bundle getIntent = getIntent().getExtras();
        if (getIntent != null) {
            title = getIntent.getString("title");
            downloadLink = getIntent.getString("downloadImage");
            photographerName = getIntent.getString("photo");
            date = getIntent.getString("date");
            location = getIntent.getString("locations");
            likes = getIntent.getString("likes");
            description = getIntent.getString("description");
            downloadLink = getIntent.getString("downloadImage");
            imageUrl = getIntent.getString("image");
        }

        Log.d(TAG, "DESCRIPTION:  " + description);
        Log.d(TAG, "LOCATION:  " + location);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        pgBar = findViewById(R.id.pgBar);
        tvMessage = findViewById(R.id.tvMessage);

        pgBar.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);

        tvPhotographerName = findViewById(R.id.tvPhotographerName);
        tvDate = findViewById(R.id.tvDate);
        cardView = findViewById(R.id.cardView);
        tvPhotographerName = findViewById(R.id.tvPhotographerName);
        tvDate = findViewById(R.id.tvDate);
        tvLocation = findViewById(R.id.tvLocation);
        tvLikes = findViewById(R.id.tvLikes);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);
        //  ivDownload = findViewById(R.id.ivDownload);
        ivFullScreen = findViewById(R.id.ivFullScreen);
        ivFullScreen.setVisibility(View.GONE);
        loadImage();
        tvPhotographerName.setText(photographerName);
        tvDate.setText(date.substring(0, 10));
        tvLocation.setText(location);
        tvLikes.setText(likes);

        tvDescription.setText(description.substring(0, 10) + "...");
        fullScreenImageView();
        minimizeFullScreen();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void loadImage() {
        pgBar.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText("Image Loading... ");
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    pgBar.setVisibility(View.GONE);
                    tvMessage.setVisibility(View.GONE);
                    return false;

                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    pgBar.setVisibility(View.GONE);
                    tvMessage.setVisibility(View.GONE);
                    return false;
                }
            }).into(ivImage);
        } else {
            Log.d(TAG, "URL NULL " + imageUrl);
        }

    }

    private void fullScreenImageView() {
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgBar.setVisibility(View.VISIBLE);
                tvMessage.setVisibility(View.VISIBLE);
                ivFullScreen.setVisibility(View.VISIBLE);
                Glide.with(ImageDetail.this).load(imageUrl).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                         pgBar.setVisibility(View.GONE);
                        tvMessage.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        ivImage.setVisibility(View.GONE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       pgBar.setVisibility(View.GONE);
                        tvMessage.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        ivImage.setVisibility(View.GONE);
                        return false;
                    }
                }).into(ivFullScreen);
            }


//            }    ivFullScreen.setVisibility(View.VISIBLE);
//                Glide.with(ImageDetail.this).load(imageUrl).into(ivFullScreen);


        });
    }

    private void minimizeFullScreen() {
        ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFullScreen.setVisibility(View.GONE);
                Glide.with(ImageDetail.this).load(imageUrl).into(ivFullScreen);
                cardView.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.VISIBLE);
            }
        });
    }


}
