package com.glue;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static java.lang.String.format;


@Controller("/recipe")
public class ApiEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ApiEndpoint.class);
    private final RecipeRepository repository;

    public ApiEndpoint(RecipeRepository repository) {
        this.repository = repository;
    }

    @Get("/{id}")
    @Produces(APPLICATION_JSON)
    public Recipe fetch(Long id) {
        LOG.info("Fetching recipe id: {}", id);
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new HttpStatusException(HttpStatus.NOT_FOUND, format("Recipe id %s not found", id))
                );
    }

    @Get("/all")
    @Produces(APPLICATION_JSON)
    public List<Recipe> fetchAll() {
        LOG.info("Fetching all recipes");
        List<Recipe> recipes = new ArrayList<>();
        repository
                .findAll().forEach(recipes::add);
        return recipes;
    }

    @Post
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public MutableHttpResponse<Recipe> saveRecipe(@Body @Valid RecipeSaveRequest request) {
        Recipe entity = new Recipe()
                .setName(request.getName())
                .setIngredients(request.getIngredients())
                .setSteps(request.getSteps())
                .setType(request.getType())
                .setPicture(request.getPicture())
                .setCreatedAt(LocalDate.now());
        repository.save(entity);
        return HttpResponse.ok(entity);
    }
}
