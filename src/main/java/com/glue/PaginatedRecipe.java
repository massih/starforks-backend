package com.glue;

import com.glue.models.RecipePreview;

import java.util.ArrayList;
import java.util.List;

public class PaginatedRecipe {
    private List<RecipePreview> recipes; //result paginated 0-20
    private int total; //total number of recipes meeting search criteria 3000000

    public PaginatedRecipe setRecipes(List<RecipePreview> recipes) {
        this.recipes = recipes;
        return this;
    }

    public PaginatedRecipe setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<RecipePreview> getRecipes() {
        return recipes;
    }

    public int getTotal() {
        return total;
    }
}
