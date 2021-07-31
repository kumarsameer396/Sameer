package com.softeek.sameer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softeek.sameer.Activity.ShowImg;
import com.softeek.sameer.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyHolder> {

    Context context;
    Activity activity;
    ArrayList<String> getFav;

    public FavouriteAdapter(Context context,Activity activity,ArrayList<String> getFav) {
        this.context=context;
        this.activity=activity;
        this.getFav=getFav;
    }

    @NonNull
    @NotNull
    @Override
    public FavouriteAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder_fav, parent, false);


        return new FavouriteAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavouriteAdapter.MyHolder holder, int position) {

        Glide.with(context).load(getFav.get(position)).into(holder.imageView);

        holder.imageView.setOnClickListener(view -> {
            Intent intent=new Intent(context, ShowImg.class);
            intent.putExtra("img",getFav.get(position));
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return getFav.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView favLay;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            favLay=itemView.findViewById(R.id.favLay);
        }
    }
}
