package com.glue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.glue.models.RecipePreview;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class PaginatedRecipe {
    private final List<RecipePreview> recipes;
    private final int total;

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
