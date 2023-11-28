package com.example.bloomroom10;

public class Category {
    private String name;
    private String description;

    public Category() {
        // Empty constructor needed for Firestore
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Add getters and setters as needed
}
