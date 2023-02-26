package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.MyError;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.repositories.RecipeRepository;
import by.it_academy.fitness.entity.IngredientEntity;
import by.it_academy.fitness.entity.ProductEntity;
import by.it_academy.fitness.entity.RecipeEntity;
import by.it_academy.fitness.service.api.IRecipeService;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class RecipeService implements IRecipeService {

    private final RecipeRepository repository;
    private final ProductService productService;

    public RecipeService(RecipeRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Override
    public void add(RecipeForCU recipeForCU) {
        if(recipeForCU == null){
            throw new SingleErrorResponse("error", "Заполните форму для регистрации нового пользователя.");
        }
        validation(recipeForCU);

        LocalDateTime dtCreate = LocalDateTime.now();
        LocalDateTime dtUpdate = dtCreate;

        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        List<Ingredient> ingredientList = recipeForCU.getComposition();

        ProductEntity productEntity;

        int totalWeight = 0;
        int totalCalories = 0;
        double totalProteins = 0.0;
        double totalFats = 0.0;
        double totalCarbohydrates = 0.0;

        for (Ingredient ingredient : ingredientList) {
            UUID productUUID = ingredient.getProduct().getUuid();
            productEntity = productService.getProductEntity(productUUID);
            double part = 1d * ingredient.getWeight() / productEntity.getWeight();
            ingredientEntityList.add(new IngredientEntity(productEntity, ingredient.getWeight()));

            totalWeight += ingredient.getWeight();
            totalCalories += productEntity.getCalories() * part;
            totalProteins += productEntity.getProteins() * part;
            totalFats += productEntity.getFats() * part;
            totalCarbohydrates += productEntity.getCarbohydrates() * part;
        }

        RecipeEntity recipeEntity = new RecipeEntity(UUID.randomUUID(),
                dtCreate,
                dtUpdate,
                recipeForCU.getTitle(),
                ingredientEntityList,
                totalWeight,
                totalCalories,
                totalProteins,
                totalFats,
                totalCarbohydrates);

        repository.save(recipeEntity);
    }

    @Override
    public Page <Recipe> getPageRecipes(int page, int size) {
        Recipe.RecipeBuilder recipeBuilder = Recipe.RecipeBuilder.create();
        org.springframework.data.domain.Page<RecipeEntity> recipeEntityPage = repository.findAll(PageRequest.of(page, size));

        List <Recipe> recipes = new ArrayList<>();

        List<RecipeEntity> recipeEntities = recipeEntityPage.toList();

        for (RecipeEntity recipeEntity : recipeEntities) {
            recipes.add(recipeBuilder.setUuid(recipeEntity.getUuid())
                    .setDtCreate(recipeEntity.getDtCreate())
                    .setDtUpdate(recipeEntity.getDtUpdate())
                    .setTitle(recipeEntity.getTitle())
                    .setComposition(listEntityToListDTO(recipeEntity.getComposition()))
                    .setWeight(recipeEntity.getWeight())
                    .setCalories(recipeEntity.getCalories())
                    .setProteins(recipeEntity.getProteins())
                    .setFats(recipeEntity.getFats())
                    .setCarbohydrates(recipeEntity.getCarbohydrates()).build());
        }

        Page<Recipe> pageOfRecipe = new Page<>(recipeEntityPage.getNumber(),
                recipeEntityPage.getSize(),
                recipeEntityPage.getTotalPages(),
                recipeEntityPage.getTotalElements(),
                recipeEntityPage.isFirst(),
                recipeEntityPage.getNumberOfElements(),
                recipeEntityPage.isLast(),
                recipes);
        return pageOfRecipe;
    }

    @Override
    public void updateRecipe(UUID uuid, long dtUpdate, RecipeForCU recipeForCU) {
        if(uuid == null || recipeForCU == null){
            throw new SingleErrorResponse("error","Не переданы параметры для обновления");
        }
        validation(recipeForCU);

        if(repository.findById(uuid).isEmpty()){
            throw new SingleErrorResponse("error","Такого рецепта для обновления не существует");
        }

        RecipeEntity recipeEntity = repository.findById(uuid).get();

        if(recipeEntity.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != (dtUpdate)){
            throw new SingleErrorResponse("error", "У вас не актуальная версия");
        }
        if(repository.findByTitle(recipeForCU.getTitle()).isPresent() && !repository.findByTitle(recipeForCU.getTitle()).get().getUuid().equals(uuid)){
            throw new SingleErrorResponse("error", "Указанное название рецепта уже используется.");
        }
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        List<Ingredient> ingredientList = recipeForCU.getComposition();

        ProductEntity productEntity;
        int totalWeight = 0;
        int totalCalories = 0;
        double totalProteins = 0.0;
        double totalFats = 0.0;
        double totalCarbohydrates = 0.0;

        for (Ingredient ingredient : ingredientList) {
            productEntity = productService.getProductEntity(ingredient.getProduct().getUuid());
            double part = 1d * ingredient.getWeight() / productEntity.getWeight();
            ingredientEntityList.add(new IngredientEntity(productEntity, ingredient.getWeight()));

            totalWeight += ingredient.getWeight();
            totalCalories += productEntity.getCalories() * part;
            totalProteins += productEntity.getProteins() * part;
            totalFats += productEntity.getFats() * part;
            totalCarbohydrates += productEntity.getCarbohydrates() * part;
        }

        recipeEntity.setTitle(recipeForCU.getTitle());
        recipeEntity.setComposition(ingredientEntityList);
        recipeEntity.setWeight(totalWeight);
        recipeEntity.setCalories(totalCalories);
        recipeEntity.setProteins(totalProteins);
        recipeEntity.setFats(totalFats);
        recipeEntity.setCarbohydrates(totalCarbohydrates);
        repository.save(recipeEntity);
    }

    public List<Ingredient> listEntityToListDTO (List<IngredientEntity> ingredientEntityList){
        List<Ingredient> ingredientList = new ArrayList<>();
        for (IngredientEntity ingredientEntity : ingredientEntityList) {
            ingredientList.add(new Ingredient(productEntityToProduct(ingredientEntity.getProduct()),
                    ingredientEntity.getWeight(),
                    ingredientEntity.getProduct().getCalories() * ingredientEntity.getWeight() / ingredientEntity.getProduct().getWeight(),
                    Math.round((ingredientEntity.getProduct().getProteins() * ingredientEntity.getWeight() / ingredientEntity.getProduct().getWeight()) * 100.0)/100.0,
                    Math.round((ingredientEntity.getProduct().getFats() * ingredientEntity.getWeight() / ingredientEntity.getProduct().getWeight()) * 100.0)/100.0,
                    Math.round((ingredientEntity.getProduct().getCarbohydrates() * ingredientEntity.getWeight() / ingredientEntity.getProduct().getWeight()) * 100.0)/100.0));
        }
        return ingredientList;
    }

    public Product productEntityToProduct (ProductEntity productEntity) {
        Product.ProductBuilder productBuilder = Product.ProductBuilder.create();

        Product product = productBuilder.setUuid(productEntity.getUuid())
                .setDt_create(productEntity.getDtCreate())
                .setDt_update(productEntity.getDtUpdate())
                .setTitle(productEntity.getTitle())
                .setWeight(productEntity.getWeight())
                .setCalories(productEntity.getCalories())
                .setProteins(productEntity.getProteins())
                .setFats(productEntity.getFats())
                .setCarbohydrates(productEntity.getCarbohydrates()).build();
        return product;
    }

    public void validation(RecipeForCU recipeForCU){
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(recipeForCU.getTitle() == null || recipeForCU.getTitle().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "title"));
        }
        if(recipeForCU.getComposition() == null || recipeForCU.getComposition().size() == 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "composition"));
        }
        List<Ingredient> ingredientEntityList = recipeForCU.getComposition();
        for (Ingredient ingredient : ingredientEntityList) {
            if(ingredient.getWeight() == 0){
                if(multipleError.getLogref() == null){
                    multipleError.setLogref("Проверьте введенные данные!");
                }
                multipleError.setErrors(new MyError("Поле не может быть равно 0.", "weight"));
            }
            if(ingredient.getWeight() < 0){
                if(multipleError.getLogref() == null){
                    multipleError.setLogref("Проверьте введенные данные!");
                }
                multipleError.setErrors(new MyError("Поле не может быть отрицательным.", "weight"));
            }
            if(ingredient.getProduct() == null){
                if(multipleError.getLogref() == null){
                    multipleError.setLogref("Проверьте введенные данные!");
                }
                multipleError.setErrors(new MyError("Поле не заполнено.", "product"));
            }
            if(ingredient.getProduct().getUuid() == null){
                if(multipleError.getLogref() == null){
                    multipleError.setLogref("Проверьте введенные данные!");
                }
                multipleError.setErrors(new MyError("Поле не заполнено.", "product"));
            }
//            if(repository.findById(ingredient.getProduct().getUuid()).isEmpty()){
//                if(multipleError.getLogref() == null){
//                    multipleError.setLogref("Проверьте введенные данные!");
//                }
//                multipleError.setErrors(new MyError("Продукта с uuid " + ingredient.getProduct().getUuid()
//                        + " не найдено.", "product: uuid"));
//            }
        }
        if(multipleError.getErrors().size()>0){
            throw multipleError;
        }
    }
}
