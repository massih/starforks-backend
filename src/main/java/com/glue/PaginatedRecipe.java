package com.glue;

import com.glue.models.RecipePreview;

import java.util.ArrayList;
import java.util.List;

public class PaginatedRecipe {
    private final List<RecipePreview> recipes; //result paginated 0-20
    private final int total; //total number of recipes meeting search criteria 3000000

    public PaginatedRecipe(List<RecipePreview> recipes, int total) {
        this.recipes = recipes;
        this.total = total;
    }

    public List<RecipePreview> getRecipes() {
        return recipes;
    }

    public int getTotal() {
        return total;
    }
}
