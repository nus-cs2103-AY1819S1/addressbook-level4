package seedu.souschef.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.DayComparator;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;


/**
 * Wraps all data at the application
 * content level
 * Duplicates are not allowed (by .isSame comparison)
 */
public class AppContent implements ReadOnlyAppContent {

    private final UniqueList<Recipe> recipes;
    private final UniqueList<Tag> tags;
    private final UniqueList<Ingredient> ingredients;
    private final UniqueList<CrossRecipe> crossRecipes;
    private final UniqueList<HealthPlan> healthPlans;
    private final UniqueList<Day> mealPlanner;
    private final UniqueList<Favourites> favourites;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recipes = new UniqueList<>();
        tags = new UniqueList<>();
        ingredients = new UniqueList<>();
        crossRecipes = new UniqueList<>();
        healthPlans = new UniqueList<>();
        mealPlanner = new UniqueList<>();
        favourites = new UniqueList<>();
    }

    public AppContent() {}

    /**
     * Creates an AppContent using the recipes in the {@code toBeCopied}
     */
    public AppContent(ReadOnlyAppContent toBeCopied) {
        this();
        resetData(toBeCopied);

    }

    //// list overwrite operations
    /**
     * Resets the existing data of this {@code AppContent} with {@code newData}.
     */
    public void resetData(ReadOnlyAppContent newData) {
        requireNonNull(newData);
        this.recipes.set(newData.getObservableRecipeList());
        this.tags.set(newData.getObservableTagList());
        this.ingredients.set(newData.getObservableIngredientList());
        this.crossRecipes.set(newData.getObservableCrossRecipeList());
        this.healthPlans.set(newData.getObservableHealthPlanList());
        this.mealPlanner.set(newData.getObservableMealPlanner());
        this.favourites.set(newData.getObservableFavouritesList());
    }

    /**
     *
     * Function call to include new data into the app data instead of deleting old data
     */
    public void includeData(ReadOnlyAppContent newData) {
        requireNonNull(newData);
        if (newData.getObservableRecipeList().size() > 0) {
            this.recipes.set(newData.getObservableRecipeList());
        }
        if (newData.getObservableHealthPlanList().size() > 0) {
            this.healthPlans.set(newData.getObservableHealthPlanList());
        }
        if (newData.getObservableIngredientList().size() > 0) {
            this.ingredients.set(newData.getObservableIngredientList());
        }
        if (newData.getObservableCrossRecipeList().size() > 0) {
            this.crossRecipes.set(newData.getObservableCrossRecipeList());
        }
        if (newData.getObservableMealPlanner().size() > 0) {
            List<Day> dayList = newData.getObservableMealPlanner();
            List<Recipe> fullRecipeList = recipes.asUnmodifiableObservableList();

            for (Day d : dayList) {
                for (Meal m : d.getMeals()) {
                    for (Recipe r : fullRecipeList) {
                        if (!m.isEmpty() && m.getRecipe().isSame(r)) {
                            m.setRecipe(r);
                        }
                    }
                }
            }

            this.mealPlanner.set(newData.getObservableMealPlanner());
            this.mealPlanner.sortList(new DayComparator());
        }
        if (newData.getObservableFavouritesList().size() > 0) {
            this.favourites.set(newData.getObservableFavouritesList());
        }
    }

    //// recipe-level operations
    public UniqueList<Recipe> getRecipes() {
        return recipes;
    }

    //// tag-level operations
    public UniqueList<Tag> getTags() {
        return tags;
    }

    //// ingredient-level operations
    public UniqueList<Ingredient> getIngredients() {
        return ingredients;
    }

    //// cross recipe-level operations
    public UniqueList<CrossRecipe> getCrossRecipes() {
        if (crossRecipes.size() == 0) {
            for (Recipe recipe : recipes) {
                crossRecipes.add(new CrossRecipe(recipe, recipe.getIngredients()));
            }
        }
        return crossRecipes;
    }

    //healthplan level operations
    public UniqueList<HealthPlan> getHealthPlans() {
        return healthPlans;
    }

    // meal planner level operations
    public UniqueList<Day> getMealPlanner() {
        return mealPlanner;
    }

    // favourite-level operations
    public UniqueList<Favourites> getFavourites() {
        return favourites;
    }

    //// util methods
    @Override
    public String toString() {
        return recipes.asUnmodifiableObservableList().size() + " recipes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recipe> getObservableRecipeList() {
        return recipes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getObservableTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Ingredient> getObservableIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<CrossRecipe> getObservableCrossRecipeList() {
        return crossRecipes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<HealthPlan> getObservableHealthPlanList() {
        return healthPlans.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Day> getObservableMealPlanner() {
        return mealPlanner.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Favourites> getObservableFavouritesList() {
        return favourites.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppContent // instanceof handles nulls
                && recipes.equals(((AppContent) other).recipes)
                && healthPlans.equals(((AppContent) other).healthPlans)
                && mealPlanner.equals(((AppContent) other).mealPlanner)
                && ingredients.equals(((AppContent) other).ingredients)
                && favourites.equals(((AppContent) other).ingredients));
    }

    @Override
    public int hashCode() {
        return recipes.hashCode();
    }
}
