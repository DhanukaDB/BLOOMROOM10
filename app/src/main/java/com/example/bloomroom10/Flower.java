package com.example.bloomroom10;
import java.util.HashMap;
import java.util.Map;

// Flower.java

public class Flower {

    private String id;
    private String flowerName;
    private String flowerDescription;
    private double flowerPrice;
    private String flowerCategory;
    private String flowerImageUrl;
    private String offerPercentage;

    // Empty constructor required for Firebase
    public Flower() {
    }

    public Flower(String flowerName, String flowerDescription, double flowerPrice,
                  String flowerCategory, String flowerImageUrl, String offerPercentage) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerPrice = flowerPrice;
        this.flowerCategory = flowerCategory;
        this.flowerImageUrl = flowerImageUrl;
        this.offerPercentage = offerPercentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter methods
    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public void setFlowerDescription(String flowerDescription) {
        this.flowerDescription = flowerDescription;
    }

    public double getFlowerPrice() {
        return flowerPrice;
    }

    public void setFlowerPrice(double flowerPrice) {
        this.flowerPrice = flowerPrice;
    }

    public String getFlowerCategory() {
        return flowerCategory;
    }

    public void setFlowerCategory(String flowerCategory) {
        this.flowerCategory = flowerCategory;
    }

    public String getFlowerImageUrl() {
        return flowerImageUrl;
    }

    public void setFlowerImageUrl(String flowerImageUrl) {
        this.flowerImageUrl = flowerImageUrl;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }
}
