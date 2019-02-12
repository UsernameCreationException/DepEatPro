package com.elis.DepEat.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elis.DepEat.R;
import com.elis.DepEat.datamodels.Order;
import com.elis.DepEat.datamodels.Products;
import com.elis.DepEat.datamodels.Restaurant;
import com.elis.DepEat.ui.adapter.OrderProductsAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, OrderProductsAdapter.onItemRemovedListener{

    private TextView restaturantTv,restaurantAdress,totalTv;
    private RecyclerView productRv;
    private Button payBtn;
    private LinearLayoutManager layoutManager;
    private OrderProductsAdapter adapter;




    private Order order;
    private float total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        restaturantTv = findViewById(R.id.restaurant_name);
        restaurantAdress = findViewById(R.id.restaurant_adress);
        totalTv = findViewById(R.id.total_tv);
        productRv = findViewById(R.id.product_rv);
        payBtn = findViewById(R.id.pay_btn);

        // Initialize datamodel object
        order = getOrder();
        total = order.getTotal();

        // setup recyclerview
        layoutManager = new LinearLayoutManager(this);
        productRv.setLayoutManager(layoutManager);
        adapter = new OrderProductsAdapter(this,order.getProducts(),order.getRestaurant().getMinimo());
        adapter.setOnItemRemovedListener(this);
        productRv.setAdapter(adapter);

        //set click listener for button
        payBtn.setOnClickListener(this);

        bindData();

    }


    private void bindData(){
        restaturantTv.setText(order.getRestaurant().getNome());
        restaurantAdress.setText(order.getRestaurant().getIndirizzo());
        totalTv.setText("Totale: "+String.valueOf(order.getTotal()));



    }


    //TODO hardcoded

    private Order getOrder(){

        Order mockOrder =  new Order();
        mockOrder.setProducts(getProducts());
        mockOrder.setRestaurant(getRestaurant());
        float total = 0;
        for(Products x : mockOrder.getProducts()){
            total += (x.getCosto()*x.getQuantita());
        }
        mockOrder.setTotal(total);

        return mockOrder;
    }


    private Restaurant getRestaurant() {
        return new Restaurant("Fraschetta", "Via le mani dal naso", 10);
    }

    //TODO hardcoded
    private ArrayList<Products> getProducts() {
        ArrayList<Products> products = new ArrayList<>();
        products.add(new Products("McMenu", 5,2));
        products.add(new Products("Hamburger", 3,3));
        products.add(new Products("Toast", 1,4));

        return products;

    }

    @Override
    public void onClick(View view) {
        //TODO manageClick
    }

    @Override
    public void onItemRemoved(float subtotal) {
        updateTotal(subtotal);
    }

    private void updateTotal(float subtotal) {
        if(total == 0) return;
        total -=subtotal;
        totalTv.setText("Totale: "+String.valueOf(total));
    }
}