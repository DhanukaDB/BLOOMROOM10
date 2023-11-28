package com.example.bloomroom10;

public class Category {
    private String id;
    private String name;
    private String description;

    public Category() {
        // Empty constructor needed for Firestore
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
