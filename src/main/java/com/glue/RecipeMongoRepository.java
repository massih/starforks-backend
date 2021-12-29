package com.glue;

import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

import static dev.morphia.query.Sort.descending;
import static dev.morphia.query.experimental.filters.Filters.eq;
import static dev.morphia.query.experimental.filters.Filters.text;


public class RecipeMongoRepository {
    private final Datastore datastore;

    @Inject
    public RecipeMongoRepository(MongoDbClient mongoDbClient) {
        this.datastore = mongoDbClient.getDataStore();
    }

    public RecipeMongo save(RecipeMongo recipe) {
        return datastore.save(recipe);
    }

    public Optional<RecipeMongo> find(String id) {
        RecipeMongo recipe = datastore.find(RecipeMongo.class).filter(eq("id", id)).iterator().tryNext();
        return Optional.ofNullable(recipe);
    }

    public List<RecipeMongo> findAll(int skip, int limit) {
        return datastore.find(RecipeMongo.class).iterator(
                new FindOptions()
                        .sort(descending("createdAt"))
                        .skip(skip)
                        .limit(limit)
        ).toList();
    }

    public List<RecipeMongo> findAllFiltered(int skip, int limit, String searchWords) {
        return datastore.find(RecipeMongo.class)
                .filter(text(searchWords)
                        .caseSensitive(false)
                        .diacriticSensitive(false)
                )
                .iterator().toList();
    }
}