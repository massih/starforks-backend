package com.glue;

import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import static dev.morphia.query.Sort.descending;
import static dev.morphia.query.experimental.filters.Filters.eq;
import static dev.morphia.query.experimental.filters.Filters.text;


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

    public List<Recipe> findAll(int skip, int limit) {
        return datastore.find(Recipe.class).iterator(
                new FindOptions()
                        .sort(descending("createdAt"))
                        .skip(skip)
                        .limit(limit)
        ).toList();
    }

    public List<Recipe> findAll(int skip, int limit, String searchWords) {
        if (searchWords.isEmpty()) {
            return findAll(skip, limit);
        }
        return datastore.find(Recipe.class)
                .filter(text(searchWords)
                        .caseSensitive(false)
                        .diacriticSensitive(false)
                )
                .iterator(new FindOptions()
                        .sort(descending("createdAt"))
                        .skip(skip)
                        .limit(limit))
                .toList();
    }
}