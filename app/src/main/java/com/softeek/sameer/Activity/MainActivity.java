package com.softeek.sameer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeek.sameer.Adapter.PhotoAdapter;
import com.softeek.sameer.Model.PhotoModel;
import com.softeek.sameer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText searchBox;
    RecyclerView recyclerView;
    Button searchBtn;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PhotoModel> photoModelArrayList = new ArrayList<>();
    PhotoAdapter photoAdapter;
    FloatingActionButton next, prev;
    ImageView favImgBtn;
    ShimmerFrameLayout shimmerFrameLayout;

    String url = "https://api.flickr.com/services/rest/?method=";
    String method = "flickr.photos.search";
    String api_key = "062a6c0c49e4de1d78497d13a7dbb360";
    String tags = "";
    String format = "json";
    int nojsoncallback = 1;
    int per_page = 2;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();

        searchBtn.setOnClickListener(view -> {

            if (searchBox.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Search box is empty.",Toast.LENGTH_SHORT).show();
            }
            tags = searchBox.getText().toString();
            if (isNetworkConnected()){
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                retrieveData();
            }
            else {
                Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(view -> {
            if (isNetworkConnected()){
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                page++;
                retrieveData();
            }
            else {
                Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_SHORT).show();
            }
        });

        prev.setOnClickListener(view -> {
            if (isNetworkConnected()){
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                page--;
                retrieveData();
            }
            else {
                Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_SHORT).show();
            }
        });

        favImgBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavImg.class);
            startActivity(intent);
        });

    }


    void init() {
        searchBox = findViewById(R.id.searchBox);
        recyclerView = findViewById(R.id.recyclerView);
        searchBtn = findViewById(R.id.button);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        favImgBtn = findViewById(R.id.imageView3);
        shimmerFrameLayout = findViewById(R.id.shimmer);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        next.setVisibility(View.GONE);
        prev.setVisibility(View.GONE);
    }


    public void retrieveData() {

        photoModelArrayList.clear();

        StringRequest request = new StringRequest(Request.Method.GET, url + method + "&api_key=" + api_key + "&tags=" + tags +
                "&format=" + format + "&nojsoncallback=" + nojsoncallback + "&per_page=" + per_page + "&page=" + page,
                response -> {

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String stat = jsonObject.getString("stat");
                        JSONObject photoJson = jsonObject.getJSONObject("photos");

                        page = photoJson.getInt("page");
                        next.setVisibility(View.VISIBLE);

                        if (page > 1) {
                            prev.setVisibility(View.VISIBLE);
                        } else {
                            prev.setVisibility(View.GONE);
                        }

                        JSONArray photoArray = photoJson.getJSONArray("photo");

                        if (stat.equals("ok")) {

                            for (int i = 0; i < photoArray.length(); i++) {

                                JSONObject pJson = photoArray.getJSONObject(i);

                                photoModelArrayList.add(new PhotoModel(pJson.getString("id"),
                                        pJson.getString("owner"),
                                        pJson.getString("secret"),
                                        pJson.getString("server"),
                                        pJson.getInt("farm"),
                                        pJson.getString("title"),
                                        pJson.getInt("ispublic"),
                                        pJson.getInt("isfriend"),
                                        pJson.getInt("isfamily")));

                                photoAdapter = new PhotoAdapter(getApplicationContext(), MainActivity.this, photoModelArrayList, getListFromLocal("FavItems"));
                                recyclerView.setAdapter(photoAdapter);

                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                , error ->
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public ArrayList<String> getListFromLocal(String key) {
        SharedPreferences prefs = getSharedPreferences("Sameer", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}