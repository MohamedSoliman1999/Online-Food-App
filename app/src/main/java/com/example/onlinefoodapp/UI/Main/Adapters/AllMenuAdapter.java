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
import com.example.onlinefoodapp.Models.Popular;
import com.example.onlinefoodapp.R;
import com.example.onlinefoodapp.UI.FoodDetails.FoodDetails;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllMenuAdapter extends RecyclerView.Adapter<AllMenuAdapter.AllMenuViewHolder> {

    Context context;
    List<Allmenu> allmenuList=new ArrayList<>();
int choice=0;
    public AllMenuAdapter(Context context,int x) {
        this.context = context;
        choice=x;
    }
    public void setList(List<Allmenu> arrayList) {
        this.allmenuList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AllMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(choice==0){
            view = LayoutInflater.from(context).inflate(R.layout.allmenu_recycler_items, parent, false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.favourite_recycler_items, parent, false);
        }

        return new AllMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMenuViewHolder holder, final int position) {

        holder.allMenuName.setText(allmenuList.get(position).getName());
        holder.allMenuPrice.setText("ُ$ "+allmenuList.get(position).getPrice());
        holder.allMenuTime.setText(allmenuList.get(position).getDeliveryTime());
        holder.allMenuRating.setText(allmenuList.get(position).getRating());
        holder.allMenuCharges.setText(allmenuList.get(position).getDeliveryCharges());
        //holder.allMenuNote.setText(allmenuList.get(position).getNote());

        Glide.with(context).load(allmenuList.get(position).getImageUrl()).into(holder.allMenuImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", allmenuList.get(position).getName());
                i.putExtra("price", allmenuList.get(position).getPrice());
                i.putExtra("rating", allmenuList.get(position).getRating());
                i.putExtra("image", allmenuList.get(position).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allmenuList.size();
    }

    public static class AllMenuViewHolder extends RecyclerView.ViewHolder{

        TextView allMenuName, allMenuNote, allMenuRating, allMenuTime, allMenuCharges, allMenuPrice;
        ImageView allMenuImage;

        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            allMenuName = itemView.findViewById(R.id.all_menu_name);
            //allMenuNote = itemView.findViewById(R.id.all_menu_note);
            allMenuCharges = itemView.findViewById(R.id.all_menu_delivery_charge);
            allMenuRating = itemView.findViewById(R.id.all_menu_rating);
            allMenuTime = itemView.findViewById(R.id.all_menu_deliverytime);
            allMenuPrice = itemView.findViewById(R.id.all_menu_price);
            allMenuImage = itemView.findViewById(R.id.all_menu_image);
        }
    }

}
