package com.elis.DepEat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {
    private String nome;
    private String imageUrl;
    private float minimo;
    private String indirizzo;
    private ArrayList<Products> productsArrayList;
    private String id;

    public static final String ENDPOINT = "restaurants";

    public Restaurant(String nome, float minimo, String imageUrl) {
        this.nome = nome;
        this.minimo = minimo;
        this.imageUrl = imageUrl;
        this.productsArrayList = getData();
    }

    public Restaurant(String nome, String indirizzo, int minimo){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.minimo = minimo;
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        nome = jsonRestaurant.getString("name");
        indirizzo = jsonRestaurant.getString("address");
        minimo = Float.valueOf(jsonRestaurant.getString("min_order"));
        imageUrl = jsonRestaurant.getString("image_url");

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getMinimo() {
        return minimo;
    }

    public void setMinimo(float minimo) {
        this.minimo = minimo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Products> getProdotti() {
        return productsArrayList;
    }

    public void setProdotti(ArrayList<Products> prodotti) {
        this.productsArrayList = prodotti;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    private ArrayList<Products> getData(){
        productsArrayList = new ArrayList<>();

        Products hamburger = new Products("Hamburger ",1, 0);
        Products toast = new Products("Toast", 2, 0);
        Products patatine = new Products("Patatine", 0.50f, 0);
        Products cocaCola = new Products("Coca-Cola", 1.50f, 0);
        Products Sprite = new Products("Sprite",1.60f, 0);
        Products pizza = new Products("Pizza", 10,0);

        productsArrayList.add(hamburger);
        productsArrayList.add(toast);
        productsArrayList.add(patatine);
        productsArrayList.add(cocaCola);
        productsArrayList.add(Sprite);
        productsArrayList.add(pizza);

        return productsArrayList;
    }
}
