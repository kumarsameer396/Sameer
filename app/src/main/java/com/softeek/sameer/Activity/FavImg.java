package com.softeek.sameer.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeek.sameer.Adapter.FavouriteAdapter;
import com.softeek.sameer.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class FavImg extends AppCompatActivity {

    RecyclerView favRecycler;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> getFav = new ArrayList<>();
    FavouriteAdapter favouriteAdapter;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_img);
        Objects.requireNonNull(getSupportActionBar()).hide();

        init();
        getFav = getListFromLocal("FavItems");

        if (getFav == null) {
            Toast.makeText(getApplicationContext(), "No Favourite Images", Toast.LENGTH_SHORT).show();
        } else {
            favouriteAdapter = new FavouriteAdapter(getApplicationContext(), FavImg.this, getFav);
            favRecycler.setAdapter(favouriteAdapter);
        }

        back.setOnClickListener(view -> super.onBackPressed());
    }

    void init() {
        favRecycler = findViewById(R.id.favRecycler);
        back = findViewById(R.id.imageView2);

        linearLayoutManager = new LinearLayoutManager(this);
        favRecycler.setLayoutManager(linearLayoutManager);
    }

    public ArrayList<String> getListFromLocal(String key) {
        SharedPreferences prefs = getSharedPreferences("Sameer", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}