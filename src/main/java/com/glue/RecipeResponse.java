package com.glue;

import java.time.LocalDate;

public class RecipeResponse {
    private String id;
    private String name;
    private String ingredients;
    private String steps;
    private String type;
    private String picture;
    private LocalDate createdAt;

    public String getId() {
        return id;
    }

    public RecipeResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public RecipeResponse setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getSteps() {
        return steps;
    }

    public RecipeResponse setSteps(String steps) {
        this.steps = steps;
        return this;
    }

    public String getType() {
        return type;
    }

    public RecipeResponse setType(String type) {
        this.type = type;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public RecipeResponse setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public RecipeResponse setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
