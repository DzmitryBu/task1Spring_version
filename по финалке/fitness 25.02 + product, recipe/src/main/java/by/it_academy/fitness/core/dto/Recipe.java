package by.it_academy.fitness.core.dto;

import by.it_academy.fitness.entity.IngredientEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Recipe {
    private UUID uuid;
    private long dtCreate;

    private long dtUpdate;
    private String title;
    private List<Ingredient> composition;

    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public Recipe(UUID uuid,
                  long dtCreate,
                  long dtUpdate,
                  String title,
                  List<Ingredient> composition,
                  int weight,
                  int calories,
                  double proteins,
                  double fats,
                  double carbohydrates) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public UUID getUuid() {
        return uuid;
    }
    public long getDtCreate() {
        return dtCreate;
    }
    public long getDtUpdate() {
        return dtUpdate;
    }
    public String getTitle() {
        return title;
    }
    public List<Ingredient> getComposition() {
        return composition;
    }
    public int getWeight() {
        return weight;
    }
    public int getCalories() {
        return calories;
    }
    public double getProteins() {
        return proteins;
    }
    public double getFats() {
        return fats;
    }
    public double getCarbohydrates() {
        return carbohydrates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return dtCreate == recipe.dtCreate && dtUpdate == recipe.dtUpdate && weight == recipe.weight && calories == recipe.calories && Double.compare(recipe.proteins, proteins) == 0 && Double.compare(recipe.fats, fats) == 0 && Double.compare(recipe.carbohydrates, carbohydrates) == 0 && Objects.equals(uuid, recipe.uuid) && Objects.equals(title, recipe.title) && Objects.equals(composition, recipe.composition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, title, composition, weight, calories, proteins, fats, carbohydrates);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", title='" + title + '\'' +
                ", composition=" + composition +
                ", weight=" + weight +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                '}';
    }

    public  static class RecipeBuilder{
        private UUID uuid;
        private long dtCreate;
        private long dtUpdate;
        private String title;
        private List<Ingredient> composition;
        private int weight;
        private int calories;
        private double proteins;
        private double fats;
        private double carbohydrates;

        private RecipeBuilder() {
        }
        public static RecipeBuilder create(){
            return new RecipeBuilder();
        }

        public RecipeBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public RecipeBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate.toEpochSecond(ZoneOffset.UTC);
            return this;
        }

        public RecipeBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate.toEpochSecond(ZoneOffset.UTC);
            return this;
        }

        public RecipeBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public RecipeBuilder setComposition(List<Ingredient> composition) {
            this.composition = composition;
            return this;
        }

        public RecipeBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public RecipeBuilder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public RecipeBuilder setProteins(double proteins) {
            this.proteins = proteins;
            return this;
        }

        public RecipeBuilder setFats(double fats) {
            this.fats = fats;
            return this;
        }

        public RecipeBuilder setCarbohydrates(double carbohydrates) {
            this.carbohydrates = carbohydrates;
            return this;
        }

        public Recipe build(){
            return new Recipe(uuid, dtCreate, dtUpdate, title, composition, weight, calories, proteins, fats, carbohydrates);
        }
    }

}
