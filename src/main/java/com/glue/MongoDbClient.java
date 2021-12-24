package com.glue;

import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public class MongoDbClient {
    private final MongoClient client;

    @Inject
    public MongoDbClient(MongoClient client) {
        this.client = client;
    }

    public Datastore getDataStore() {
        Datastore ds = Morphia.createDatastore(client, "starforks");
        ds.getMapper().mapPackage("com.glue");
        ds.ensureIndexes();
        return ds;
    }
}
