package com.glue;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedPart;
import io.micronaut.http.server.multipart.MultipartBody;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static io.micronaut.http.MediaType.MULTIPART_FORM_DATA;
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
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_JSON)
    public Flux<Object> saveRecipe(@Body MultipartBody body) {
        LOG.info("Saving recipe!");
        return Flux.create(emitter -> {
            body.subscribe(new Subscriber<CompletedPart>() {
                private Subscription s;
                List<String> datas = new ArrayList<>();

                @Override
                public void onSubscribe(Subscription subscription) {
                    this.s = subscription;
                    s.request(200);
                }

                @Override
                public void onNext(CompletedPart completedPart) {
                    try {
                        datas.add(new String(completedPart.getBytes(), StandardCharsets.UTF_8));
                        s.request(200);
                        System.out.println("datas = " + datas);
                    } catch (IOException e) {
                        s.cancel();
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("throwable = " + throwable);
                }

                @Override
                public void onComplete() {
                    System.out.println("DONE");
//                    emitter.success(String.join("|", datas));
                }
            });
        });
//        System.out.println("body = " + body);
//        System.out.println("file.toString() = " + file.toString());
//        System.out.println("request.toString() = " + request.getName());
//        RecipeMongo entity = new RecipeMongo()
//                .setName(request.getName())
//                .setIngredients(request.getIngredients())
//                .setSteps(request.getSteps())
//                .setType(request.getType())
//                .setPicture(request.getPicture())
//                .setCreatedAt(LocalDate.now());
//        return repository.save(entity);
//        return new RecipeMongo();
    }
}
