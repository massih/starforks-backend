package com.glue;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Recipe {
    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String ingredients;

    @NotNull
    private String steps;

    @NotNull
    private String type;

    private byte[] picture;

    @NotNull
    private LocalDate createdAt;

    public String getId() {
        return id;
    }

    public Recipe setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getSteps() {
        return steps;
    }

    public Recipe setSteps(String steps) {
        this.steps = steps;
        return this;
    }

    public String getType() {
        return type;
    }

    public Recipe setType(String type) {
        this.type = type;
        return this;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Recipe setPicture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Recipe setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String toString() {
        return "RecipeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", steps='" + steps + '\'' +
                ", type='" + type + '\'' +
                ", picture='" + picture + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
