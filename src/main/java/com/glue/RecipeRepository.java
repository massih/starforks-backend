package com.glue;

import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import static dev.morphia.query.Sort.descending;
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
        String regex = searchWords.replaceAll(" ", "|");
        Pattern pattern = Pattern.compile(regex);

        Query<Recipe> query = datastore.find(Recipe.class);
        return query.filter(or(
                        regex("name").pattern(pattern),
                        regex("ingredients").pattern(pattern),
                        regex("steps").pattern(pattern)
                ))
                .iterator().toList();

    }
}