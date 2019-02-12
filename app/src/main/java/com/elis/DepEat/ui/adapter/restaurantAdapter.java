package com.elis.DepEat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elis.DepEat.R;
import com.elis.DepEat.ui.activities.ShopActivity;
import com.elis.DepEat.datamodels.Restaurant;

import java.util.ArrayList;

public class restaurantAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<Restaurant> data;
    private Context context;
    private int orientation;
    private RestaurantViewHolder vh;

    public restaurantAdapter(Context context, ArrayList<Restaurant> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public void setOrientation(int orientation){
        this.orientation = orientation;
    }

    public RestaurantViewHolder getVh(){ return vh; }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_restaurant, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        vh = (RestaurantViewHolder) viewHolder;
        vh.setRestaurant(data.get(i));
        vh.restaurantNameTv.setText(data.get(i).getNome());
        vh.minimumOrderTv.setText("Spesa Minima: "+String.valueOf(data.get(i).getMinimo()));
        Glide.with(context).load(data.get(i).getImageUrl()).into(vh.restaurantImage);
        vh.itemLayout.setOrientation(orientation);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        public TextView restaurantNameTv;
        public TextView minimumOrderTv;
        public ImageView restaurantImage;
        public LinearLayout itemLayout;
        public CardView cardView;
        public Restaurant restaurant;

        public void setRestaurant(Restaurant restaurant){
            this.restaurant = restaurant;
        }

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantNameTv = itemView.findViewById(R.id.restaurantname_tv);
            minimumOrderTv = itemView.findViewById(R.id.minimumorder_tv);
            restaurantImage = itemView.findViewById(R.id.restaurantimage_iv);
            itemLayout = itemView.findViewById(R.id.item_layout);
            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
