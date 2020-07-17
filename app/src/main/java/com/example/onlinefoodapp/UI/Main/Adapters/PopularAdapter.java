package com.example.onlinefoodapp.UI.Main.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.onlinefoodapp.Models.Popular;
import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.FoodDetails.FoodDetails;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    List<Popular> popularList = new ArrayList<>();
    private Context mContext;

    public PopularAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<Popular> arrayList) {
        this.popularList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Display item cardView in parent layout in Recycle View
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.popular_recycler_items, parent, false);
        return new PopularViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final PopularViewHolder holder, final int position) {
        holder.popularName.setText(popularList.get(position).getName());

        // for image we add Glide library dependency for image fetching from server

        Glide.with(mContext).load(popularList.get(position).getImageUrl()).into(holder.popularImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, FoodDetails.class);
                i.putExtra("name", popularList.get(position).getName());
                i.putExtra("price", popularList.get(position).getPrice());
                i.putExtra("rating", popularList.get(position).getRating());
                i.putExtra("image", popularList.get(position).getImageUrl());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        ImageView popularImage;
        TextView popularName;
        public PopularViewHolder(@NonNull final View itemView) {
            super(itemView);
            popularName = itemView.findViewById(R.id.all_menu_name);
            popularImage = itemView.findViewById(R.id.all_menu_image);
        }
    }

    public Popular getWordItemAt(int id) {
        return popularList.get(id);
    }
}
