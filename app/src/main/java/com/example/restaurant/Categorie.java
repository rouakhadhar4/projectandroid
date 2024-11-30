package com.example.restaurant;

public class Categorie {
    private String imageUrl;
    private String name;

    // Constructeur vide nécessaire pour Firebase
    public Categorie() {}

    public Categorie(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    // Getters et Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

