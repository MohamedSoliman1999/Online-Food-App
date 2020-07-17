package com.example.onlinefoodapp.UI.Main.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onlinefoodapp.Models.Allmenu;
import com.example.onlinefoodapp.Models.Recommended;
import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.FoodDetails.FoodDetails;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Recommended> recommendedList=new ArrayList<>();

    public RecommendedAdapter(Context context) {
        this.context = context;
    }
    public void setList(List<Recommended> arrayList) {
        this.recommendedList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_recycler_items, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, final int position) {

        holder.recommendedName.setText(recommendedList.get(position).getName());
        holder.recommendedRating.setText(recommendedList.get(position).getRating());
        holder.recommendedCharges.setText(recommendedList.get(position).getDeliveryCharges());
        holder.recommendedDeliveryTime.setText(recommendedList.get(position).getDeliveryTime());
        holder.recommendedPrice.setText("$ "+recommendedList.get(position).getPrice());

        Glide.with(context).load(recommendedList.get(position).getImageUrl()).into(holder.recommendedImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", recommendedList.get(position).getName());
                i.putExtra("price", recommendedList.get(position).getPrice());
                i.putExtra("rating", recommendedList.get(position).getRating());
                i.putExtra("image", recommendedList.get(position).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder{

        ImageView recommendedImage;
        TextView recommendedName, recommendedRating, recommendedDeliveryTime, recommendedCharges, recommendedPrice;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);

            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedRating = itemView.findViewById(R.id.recommended_rating);
            recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
            recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);

        }
    }


}
