package one.saidin;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.time.LocalDate;


@Controller("/recipe")
public class ApiEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ApiEndpoint.class);
    private final RecipeRepository repository;

    public ApiEndpoint(RecipeRepository repository) {
        this.repository = repository;
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        LOG.info("HelloWorld EndPoint called!");
        return "Hello World!";
    }

    @Post()
    @Produces(MediaType.APPLICATION_JSON)
    public MutableHttpResponse<RecipeEntity> saveRecipe(@Body @Valid RecipeSaveRequest request) {
        RecipeEntity entity = new RecipeEntity()
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
