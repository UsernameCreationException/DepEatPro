package com.elis.DepEat.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.elis.DepEat.R;
import com.elis.DepEat.datamodels.Products;
import com.elis.DepEat.datamodels.Restaurant;

import java.util.ArrayList;

public class menuAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private Restaurant restaurant;
    private ArrayList<Products> data;
    private Context context;
    private ProductsViewHolder vh;


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public menuAdapter(Context context, ArrayList<Products> prodotti, Restaurant restaurant) {
        this.inflater = LayoutInflater.from(context);
        this.data = prodotti;
        this.context = context;
        this.restaurant = restaurant;
    }



    public interface OnQuantityChangedListener {
        void onChange(float price);

    }

    public OnQuantityChangedListener getOnQuantityChangedListener() {
        return onQuantityChangedListener;
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener onQuantityChangedListener) {
        this.onQuantityChangedListener = onQuantityChangedListener;
    }

    private OnQuantityChangedListener onQuantityChangedListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_menu, viewGroup, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Products product = data.get(i);
        vh = (ProductsViewHolder) viewHolder;
        vh.productsTv.setText(data.get(i).getNome());
        vh.productCostTv.setText(String.valueOf(data.get(i).getCosto()));
        vh.incrementalTv.setText(String.valueOf(product.getQuantita()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Products> data, Restaurant restaurant){
        this.data = data;
        this.restaurant = restaurant;
        notifyDataSetChanged();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productsTv;
        public TextView productCostTv;
        private TextView incrementalTv;
        private ImageButton decreaseBtn;
        private ImageButton increaseBtn;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);


            productsTv = itemView.findViewById(R.id.products_tv);
            productCostTv = itemView.findViewById(R.id.productcost_tv);
            incrementalTv = itemView.findViewById(R.id.incremental_tv);
            decreaseBtn = itemView.findViewById(R.id.left_arrow);
            increaseBtn = itemView.findViewById(R.id.right_arrow);

            increaseBtn.setOnClickListener(this);

            decreaseBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Products product = data.get(getAdapterPosition());


            if (view.getId() == R.id.right_arrow) {
                product.increaseQuantita();
                notifyItemChanged(getAdapterPosition());
                onQuantityChangedListener.onChange(product.getCosto());
            } else if (view.getId() == R.id.left_arrow) {
                if (product.getQuantita() == 0) return;
                product.decreaseQuantita();
                notifyItemChanged(getAdapterPosition());
                onQuantityChangedListener.onChange(product.getCosto() * -1);
            }
        }

    }

}
