package com.glue;

import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import io.micronaut.context.annotation.Context;
import jakarta.inject.Inject;


@Context
public class MongoDbClient {
    private final MongoClient client;
    private final Datastore datastore;

    @Inject
    public MongoDbClient(MongoClient client) {
        this.client = client;
        datastore = Morphia.createDatastore(client, "starforks");
        datastore.getMapper().mapPackage("com.glue");
        datastore.ensureIndexes();

    }

    public Datastore getDataStore() {
        return datastore;
    }
}
