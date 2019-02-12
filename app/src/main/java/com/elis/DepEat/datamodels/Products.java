package com.elis.DepEat.datamodels;

public class Products {

    private String nome;
    private float costo;
    private int quantita;

    public Products(String nome, float costo, int quantita) {
        this.nome = nome;
        this.costo = costo;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void increaseQuantita() {
        this.quantita++;
    }

    public void decreaseQuantita(){
        if(quantita == 0) return;
        this.quantita--;
    }

    public float getSubtotale(){
        return quantita * costo;
    }
}
