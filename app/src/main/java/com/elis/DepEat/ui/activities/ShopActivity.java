package com.elis.DepEat.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elis.DepEat.R;
import com.elis.DepEat.ui.adapter.menuAdapter;
import com.elis.DepEat.datamodels.Restaurant;


public class ShopActivity extends AppCompatActivity implements menuAdapter.OnQuantityChangedListener {

    RecyclerView productsRv;
    TextView totalTv;
    TextView minimumTv;
    ProgressBar progressBar;
    Button checkoutBtn;
    RecyclerView.LayoutManager layoutManager;
    menuAdapter adapter;
    Restaurant restaurant;

    private float total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);

        productsRv = findViewById(R.id.products_rv);
        totalTv = findViewById(R.id.total_tv);
        minimumTv = findViewById(R.id.minimumorder_tv);
        progressBar = findViewById(R.id.minimum_pb);
        checkoutBtn = findViewById(R.id.checkout_btn);

        restaurant = getRestaurant();

        progressBar.setMax((int)restaurant.getMinimo()*100);
        layoutManager = new LinearLayoutManager(this);
        adapter = new menuAdapter(this, restaurant.getProdotti());
        adapter.setOnQuantityChangedListener(this);
        productsRv.setLayoutManager(layoutManager);
        productsRv.setAdapter(adapter);
        minimumTv.setText(String.valueOf(restaurant.getMinimo()));
        totalTv.setText(String.valueOf(total));


        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this,CheckoutActivity.class));
            }
        });


    }

    private Restaurant getRestaurant() {

        return new Restaurant("Nome Ristorante",12, " ");
    }

    private void updateTotal(float item){
        total= total + item;
        totalTv.setText(String.valueOf(total));
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        startAnimation(total*100); //update della progress bar
    }

    private void startAnimation(float progress){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), (int)progress);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }
}
