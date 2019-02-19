package com.elis.DepEat.ui.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.elis.DepEat.R;
import com.elis.DepEat.backend.SharedPreferencesSettings;
import com.elis.DepEat.datamodels.Products;
import com.elis.DepEat.services.RestController;
import com.elis.DepEat.ui.adapter.menuAdapter;
import com.elis.DepEat.datamodels.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ShopActivity extends AppCompatActivity implements menuAdapter.OnQuantityChangedListener, Response.Listener<String>, Response.ErrorListener {

    private static final int LOGIN_REQUEST_CODE = 2001;

    ImageView restaurantImageIv;
    RecyclerView productsRv;
    TextView restaurantNameTv;
    TextView restaurantAddressTv;
    TextView totalTv;
    TextView minimumTv;
    ProgressBar progressBar;
    Button checkoutBtn;
    RecyclerView.LayoutManager layoutManager;
    RestController restController;
    ArrayList<Products> arrayList = new ArrayList<>();
    menuAdapter adapter;
    String idRestaurant;

    private float total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);

        idRestaurant = getIntent().getStringExtra("id");

        restaurantImageIv = findViewById(R.id.restaurantimage_iv);
        productsRv = findViewById(R.id.products_rv);
        totalTv = findViewById(R.id.total_tv);
        minimumTv = findViewById(R.id.minimumorder_tv);
        progressBar = findViewById(R.id.minimum_pb);
        checkoutBtn = findViewById(R.id.checkout_btn);
        restaurantNameTv = findViewById(R.id.restaurantname_tv);
        restaurantAddressTv = findViewById(R.id.restaurantaddress_tv);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPreferencesSettings.getBooleanFromPreferences(ShopActivity.this, "loggedIn")){
                    Intent intent = new Intent(ShopActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ShopActivity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                    Toast.makeText(ShopActivity.this,
                            R.string.login_check, Toast.LENGTH_LONG).show();
                }
                   }
        });

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat("/"+idRestaurant), this, this );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK){
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        }
    }


    private void updateTotal(float item){
        total = total + item;
        totalTv.setText(String.valueOf(total));
        checkoutBtnCheck();
    }

    private void checkoutBtnCheck() {
        if(total < Float.valueOf(minimumTv.getText().toString())){
            checkoutBtn.setEnabled(false);
        } else{
            checkoutBtn.setEnabled(true);
        }
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        startAnimation(total*100); //update della progress bar
        //TODO miglioramento progress bar
    }

    private void startAnimation(float progress){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar,"progress", progressBar.getProgress(), (int)progress);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        progressBar.invalidate();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("RequestError",error.getMessage());
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {

        try {
            JSONObject jsonObject= new JSONObject(response);
            JSONArray jsonArrayProducts = jsonObject.getJSONArray("products");
            for(int i = 0; i<jsonArrayProducts.length(); i++){
                arrayList.add(new Products(jsonArrayProducts.getJSONObject(i)));
            }
            Restaurant restaurant = new Restaurant(jsonObject);
            restaurantNameTv.setText(restaurant.getNome());
            restaurantAddressTv.setText(restaurant.getIndirizzo());
            adapter = new menuAdapter(this, arrayList, restaurant);
            adapter.setOnQuantityChangedListener(this);
            productsRv.setAdapter(adapter);
            progressBar.setMax((int)(restaurant.getMinimo()*100));
            minimumTv.setText(String.valueOf(adapter.getRestaurant().getMinimo()));
            layoutManager = new LinearLayoutManager(this);
            productsRv.setLayoutManager(layoutManager);
            totalTv.setText(String.valueOf(total));
        } catch (JSONException e) {
            Log.e("JSONError", e.getMessage());
        }

    }
}
