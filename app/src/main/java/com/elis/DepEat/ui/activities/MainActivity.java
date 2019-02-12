package com.elis.DepEat.ui.activities;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.elis.DepEat.R;
import com.elis.DepEat.backend.SharedPreferencesSettings;
import com.elis.DepEat.datamodels.Restaurant;
import com.elis.DepEat.ui.adapter.restaurantAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CoordinatorLayout mainCL;
    RecyclerView restaurantRv;
    FloatingActionButton changeVisualBtn;
    FloatingActionButton userBtn;
    FloatingActionButton cartBtn;
    RecyclerView.LayoutManager layoutManager;
    restaurantAdapter adapter;
    ArrayList<Restaurant> arrayList;

    private boolean layoutController = false;
    private boolean isFABOpen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainCL = findViewById(R.id.main_content);
        restaurantRv = findViewById(R.id.places_rw);
        changeVisualBtn = findViewById(R.id.changevisual_btn);
        userBtn = findViewById(R.id.user_btn);
        cartBtn = findViewById(R.id.checkout_btn);
        adapter = new restaurantAdapter(this, getData());
        checkLayout();
        restaurantRv.setAdapter(adapter);

        changeVisualBtn.setOnClickListener(this);
        userBtn.setOnClickListener(this);
        cartBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.changevisual_btn) {

            if(layoutController){
                setLayoutManager();
                item.setIcon(R.drawable.ic_list_black_24dp);
            }else {
                setLayoutManager();
                item.setIcon(R.drawable.ic_grid_on_black_24dp);
            }
        }
        return true;
    }

    private void checkLayout(){
        layoutController = SharedPreferencesSettings.getBooleanFromPreferences(this, "layout");
        setLayoutManager();

    }

    private void setLayoutManager(){
        if(layoutController){
            adapter.setOrientation(1);
            layoutManager = new GridLayoutManager(this, 2);
            layoutController = false;
        } else {
            adapter.setOrientation(0);
            layoutManager = new LinearLayoutManager(this);
            layoutController = true;
        }
        SharedPreferencesSettings.setSharedPreferences(this,"layout", !layoutController);
        restaurantRv.setLayoutManager(layoutManager);
    }

    private ArrayList<Restaurant> getData(){
        arrayList = new ArrayList<>();
        Restaurant MC = new Restaurant("McDonald's",13, "https://d701vexhkz032.cloudfront.net/bundles/front/media/images/favicons/favicon-512.png");
        Restaurant KFC = new Restaurant("KFC", 9, "https://upload.wikimedia.org/wikipedia/it/thumb/5/57/300px-KFC_logo_svg.png/200px-300px-KFC_logo_svg.png");
        Restaurant burger = new Restaurant("Burger King", 15, "https://upload.wikimedia.org/wikipedia/it/thumb/3/3a/Burger_King_Logo.svg/1013px-Burger_King_Logo.svg.png");
        Restaurant roadHouse = new Restaurant("Road House", 25, "https://www.gigroup.it/wp-content/uploads/2018/06/logo-3-300x168.jpg");
        Restaurant oldWW = new Restaurant("Old Wild West", 12, "https://www.datocms-assets.com/1988/1493821522-o-jpg?w=700&h=700&pad=50&fit=fillmax&bg=%23f5f8ff");
        Restaurant cMonta = new Restaurant("100Montadidos",5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoVsjLoPHRpOBGu3Vxg1bhIumeAndqyTCGG5uVZ84AJIkSGbi03A");
        Restaurant sakura = new Restaurant("SakuraSushi", 15, "https://media-cdn.tripadvisor.com/media/photo-s/04/84/07/36/sakura-sushi.jpg");
        Restaurant fAndC = new Restaurant("Fish&Chips", 10, "https://image.freepik.com/free-vector/badge-fish-chips-resturant_23-2147507311.jpg");

        arrayList.add(MC);
        arrayList.add(KFC);
        arrayList.add(burger);
        arrayList.add(roadHouse);
        arrayList.add(oldWW);
        arrayList.add(cMonta);
        arrayList.add(sakura);
        arrayList.add(fAndC);

        return arrayList;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.changevisual_btn){
            if(!isFABOpen){
                showFABMenu();
                v.animate().rotation(90).setDuration(100);
                changeVisualBtn.setImageResource(R.drawable.ic_close_black_24dp);
                //mainCL.setBackgroundColor(getResources().getColor(R.color.darknessView));
                //adapter.getVh().cardView.setCardBackgroundColor(getResources().getColor(R.color.darknessView));
            }else{
                closeFABMenu();
                changeVisualBtn.setImageResource(R.drawable.ic_touch_app_black_24dp);
                v.animate().rotation(0).setDuration(100);
                //mainCL.setBackgroundColor(getResources().getColor(R.color.defaultColor));
                //adapter.getVh().cardView.setCardBackgroundColor(getResources().getColor(R.color.defaultColor));

            }
        } else if(v.getId() == R.id.user_btn){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.checkout_btn){
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        }

    }

    private void showFABMenu() {
        isFABOpen=true;
        userBtn.animate().translationY(-getResources().getDimension(R.dimen.standard_65)).setDuration(120);
        cartBtn.animate().translationY(-getResources().getDimension(R.dimen.standard_115)).setDuration(180);

    }

    private void closeFABMenu(){
        isFABOpen=false;
        userBtn.animate().translationY(0).setDuration(120);
        cartBtn.animate().translationY(0).setDuration(180);
    }
}