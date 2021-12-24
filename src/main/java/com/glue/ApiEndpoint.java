package com.glue;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static java.lang.String.format;


@Controller("/recipe")
public class ApiEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ApiEndpoint.class);
    private final RecipeMongoRepository repository;

    @Inject
    public ApiEndpoint(RecipeMongoRepository recipeMongoRepository) {
        this.repository = recipeMongoRepository;
    }

    @Get("/{id}")
    @Produces(APPLICATION_JSON)
    public RecipeMongo fetch(String id) {
        LOG.info("Fetching recipe id: {}", id);
        return repository.find(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, format("Recipe id %s not found", id)));
    }

    @Get("/all")
    @Produces(APPLICATION_JSON)
    public List<RecipeMongo> fetchAll(@QueryValue(defaultValue = "10") int limit, @QueryValue(defaultValue = "0") int skip) {
        LOG.info("Fetching all recipes");
        return repository.findAll(skip, limit);
    }

    @Post
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public RecipeMongo saveRecipe(@Body @Valid RecipeSaveRequest request) {
        LOG.info("Saving recipe!");
        RecipeMongo entity = new RecipeMongo()
                .setName(request.getName())
                .setIngredients(request.getIngredients())
                .setSteps(request.getSteps())
                .setType(request.getType())
                .setPicture(request.getPicture())
                .setCreatedAt(LocalDate.now());
        return repository.save(entity);
    }
}
