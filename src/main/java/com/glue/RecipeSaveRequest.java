package com.glue;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Introspected
public class RecipeSaveRequest {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String ingredients;

    @NotNull
    private String steps;

    @NotNull
    private String author;

    @NotNull
    private String type;

    private String picture;

    public String getName() {
        return name;
    }

    public RecipeSaveRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public RecipeSaveRequest setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getSteps() {
        return steps;
    }

    public RecipeSaveRequest setSteps(String steps) {
        this.steps = steps;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public RecipeSaveRequest setType(String type) {
        this.type = type;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public RecipeSaveRequest setPicture(String picture) {
        this.picture = picture;
        return this;
    }
}
