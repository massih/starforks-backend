package com.glue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Base64;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static io.micronaut.http.MediaType.MULTIPART_FORM_DATA;
import static java.lang.String.format;


@Controller("/recipe")
public class ApiEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ApiEndpoint.class);
    private final RecipeRepository repository;

    @Inject
    public ApiEndpoint(RecipeRepository recipeRepository) {
        this.repository = recipeRepository;
    }

    @Get("/findOne/{id}")
    @Produces(APPLICATION_JSON)
    public RecipeResponse fetch(String id) {
        LOG.info("Fetching recipe id: {}", id);
        Recipe recipe = repository.find(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, format("Recipe id %s not found", id)));
        return new RecipeResponse()
                .setId(recipe.getId())
                .setName(recipe.getName())
                .setIngredients(recipe.getIngredients())
                .setSteps(recipe.getSteps())
                .setType(recipe.getType())
                .setPicture(Base64.getEncoder().encodeToString(recipe.getPicture()))
                .setCreatedAt(recipe.getCreatedAt());
    }

    @Get("/all/{searchWords}")
    @Produces(APPLICATION_JSON)
    public List<RecipeMongo> fetchAll(@QueryValue(defaultValue = "10") int limit, @QueryValue(defaultValue = "0") int skip, String searchWords) {
        LOG.info("Fetching all recipes");
        return repository.findAllFiltered(skip, limit, searchWords);
    }

    @Post
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_JSON)
    public Recipe saveRecipe(byte[] file, RecipeSaveRequest data) {
        Recipe entity = new Recipe()
                .setName(data.getName())
                .setIngredients(data.getIngredients())
                .setSteps(data.getSteps())
                .setType(data.getType())
                .setPicture(file)
                .setCreatedAt(LocalDate.now());
        return repository.save(entity);
    }
}
