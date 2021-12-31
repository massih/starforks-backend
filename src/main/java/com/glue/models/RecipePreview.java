package com.glue.models;

import com.glue.RecipeSaveRequest;

public class RecipePreview {
    private String id;
    private String name;
    private String picture;
    private String type;

    public String getId() {
        return id;
    }

    public RecipePreview setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipePreview setName(String name) {
        this.name = name;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public RecipePreview setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getType() {
        return type;
    }

    public RecipePreview setType(String type) {
        this.type = type;
        return this;
    }
}
