package com.glue;

import com.glue.models.RecipePreview;
import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static dev.morphia.query.experimental.filters.Filters.*;


public class RecipeRepository {
    private final Datastore datastore;

    @Inject
    public RecipeRepository(MongoDbClient mongoDbClient) {
        this.datastore = mongoDbClient.getDataStore();
    }

    public Recipe save(Recipe recipe) {
        return datastore.save(recipe);
    }

    public Optional<Recipe> find(String id) {
        Recipe recipe = datastore.find(Recipe.class).filter(eq("id", id)).iterator().tryNext();
        return Optional.ofNullable(recipe);
    }

    public PaginatedRecipe findAll(int skip, int limit) {
        System.out.println("getTotalRecipeCount() = " + getTotalRecipeCount());
        return new PaginatedRecipe()
                .setRecipes(
                        datastore.find(Recipe.class)
                                .iterator(new FindOptions()
                                        .skip(skip)
                                        .limit(limit)
                                        .projection().include("name", "id", "picture", "type"))
                                .toList()
                                .stream()
                                .map(this::recipeToRecipePreview)
                                .collect(Collectors.toList()))
                .setTotal(getTotalRecipeCount());
    }

    public PaginatedRecipe findAll(int skip, int limit, String searchWords) {
        searchWords = searchWords.trim();
        if (searchWords.isEmpty()) {
            return findAll(skip, limit);
        }
        String regex = searchWords.replaceAll(" ", "|");
        Pattern pattern = Pattern.compile(regex);
        Query<Recipe> query = datastore.find(Recipe.class);
        List<RecipePreview> recipePreviews = query.filter(or(
                        regex("name").pattern(pattern),
                        regex("ingredients").pattern(pattern),
                        regex("steps").pattern(pattern)))
                .iterator(new FindOptions()
                        .skip(skip)
                        .limit(limit)
                        .projection().include("name", "id", "picture", "type"))
                .toList()
                .stream().map(this::recipeToRecipePreview)
                .collect(Collectors.toList());

        int totalCollectionSize = (int) query.filter(or(
                        regex("name").pattern(pattern),
                        regex("ingredients").pattern(pattern),
                        regex("steps").pattern(pattern)))
                .iterator(new FindOptions())
                .toList()
                .stream().map(this::recipeToRecipePreview).count();

        return new PaginatedRecipe()
                .setRecipes(recipePreviews)
                .setTotal(totalCollectionSize);

    }

    private RecipePreview recipeToRecipePreview(Recipe recipe) {
        return new RecipePreview()
                .setPicture(recipe.getPicture())
                .setName(recipe.getName())
                .setType(recipe.getType())
                .setId(recipe.getId());
    }

    private int getTotalRecipeCount() {
        return datastore.find(Recipe.class)
                .iterator(new FindOptions()
                        .projection()
                        .include("id"))
                .toList()
                .size();

    }

}