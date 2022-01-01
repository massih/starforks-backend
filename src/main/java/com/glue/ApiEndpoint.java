package com.glue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glue.models.RecipePreview;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.multipart.CompletedPart;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
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

    @Get("/id/{id}")
    @Produces(APPLICATION_JSON)
    public RecipeResponse fetch(String id) {
        LOG.info("Fetching recipe id: {}", id);
        return repository.find(id)
                .map(entity ->
                        new RecipeResponse()
                                .setId(entity.getId())
                                .setName(entity.getName())
                                .setIngredients(entity.getIngredients())
                                .setSteps(entity.getSteps())
                                .setType(entity.getType())
                                .setPicture(Base64.getEncoder().encodeToString(entity.getPicture()))
                                .setCreatedAt(entity.getCreatedAt()))
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, format("Recipe id %s not found", id)));
    }

    @Get("/")
    @Produces(APPLICATION_JSON)
    public PaginatedRecipe fetchAll(@QueryValue(defaultValue = "10") int limit,
                                        @QueryValue(defaultValue = "0") int skip,
                                        @QueryValue(defaultValue = "") String searchWords) {
        LOG.info("Fetching all recipes containing: {}", searchWords);
        return repository.findAll(skip, limit, searchWords);
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
