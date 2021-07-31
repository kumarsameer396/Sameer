package com.softeek.sameer.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.softeek.sameer.R;

import java.util.Objects;

public class ShowImg extends AppCompatActivity {

    ImageView showImg, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);
        Objects.requireNonNull(getSupportActionBar()).hide();
        showImg = findViewById(R.id.imageView4);
        back = findViewById(R.id.imageView2);

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("img"))
                .into(showImg);

        back.setOnClickListener(view -> ShowImg.super.onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}