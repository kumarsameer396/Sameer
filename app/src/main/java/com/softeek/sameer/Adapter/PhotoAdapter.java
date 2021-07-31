package com.softeek.sameer.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.softeek.sameer.Activity.ShowImg;
import com.softeek.sameer.Model.PhotoModel;
import com.softeek.sameer.R;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList<PhotoModel> photoModelArrayList;
    ArrayList<String> favList;

    public PhotoAdapter(Context context, Activity activity, ArrayList<PhotoModel> photoModelArrayList,ArrayList<String> favList) {
        this.context = context;
        this.activity = activity;
        this.photoModelArrayList = photoModelArrayList;
        this.favList=favList;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_holder, parent, false);


        return new MyViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull PhotoAdapter.MyViewHolder holder, int position) {

        String imgUrl = "https://farm" + photoModelArrayList.get(position).getFarm() + ".staticflickr.com/" +
                photoModelArrayList.get(position).getServer() + "/" + photoModelArrayList.get(position).getId() + "_" +
                photoModelArrayList.get(position).getSecret() + ".jpg"; //_m Not using bcz resolution is low.

        Glide.with(context).load(imgUrl)
                .placeholder(R.drawable.load)
                .into(holder.imageView);

        if (favList!=null){
            if (favList.contains(imgUrl)) {
                holder.favImg.setBackground(context.getDrawable(R.drawable.fav));
            }
            else {
                holder.favImg.setBackground(context.getDrawable(R.drawable.unfav));
            }
        }

        holder.favLay.setOnClickListener(view -> {

            if (favList==null){
                favList=new ArrayList<>();
            }
            if (favList.contains(imgUrl)) {
                favList.remove(imgUrl);
                holder.favImg.setBackground(context.getDrawable(R.drawable.unfav));
            }
            else {
                favList.add(imgUrl);
                holder.favImg.setBackground(context.getDrawable(R.drawable.fav));
            }
            saveListInLocal(favList, "FavItems");
            Log.d("SizeeeNew",favList.size()+"");

        });

        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowImg.class);
            intent.putExtra("img", imgUrl);
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return photoModelArrayList.size();
    }

    public void saveListInLocal(ArrayList<String> list, String key) {

        SharedPreferences prefs = context.getSharedPreferences("Sameer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, favImg;
        CardView favLay;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            favImg = itemView.findViewById(R.id.favImg);
            favLay = itemView.findViewById(R.id.favLay);
        }
    }

}
