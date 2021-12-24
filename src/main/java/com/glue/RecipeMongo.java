package com.glue;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class RecipeMongo {
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

    private String picture;

    @NotNull
    private LocalDate createdAt;

    public String getId() {
        return id;
    }

    public RecipeMongo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeMongo setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public RecipeMongo setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getSteps() {
        return steps;
    }

    public RecipeMongo setSteps(String steps) {
        this.steps = steps;
        return this;
    }

    public String getType() {
        return type;
    }

    public RecipeMongo setType(String type) {
        this.type = type;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public RecipeMongo setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public RecipeMongo setCreatedAt(LocalDate createdAt) {
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
