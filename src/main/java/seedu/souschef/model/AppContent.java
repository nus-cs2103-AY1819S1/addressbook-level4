package seedu.souschef.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
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
    private final UniqueList<HealthPlan> healthPlans;

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
        healthPlans = new UniqueList<>();
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
        this.healthPlans.set(newData.getObservableHealthPlanList());
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

    //healthplan level operations
    public UniqueList<HealthPlan> getHealthPlans() {
        return healthPlans;
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

    public ObservableList<HealthPlan> getObservableHealthPlanList() {
        return healthPlans.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppContent // instanceof handles nulls
                && recipes.equals(((AppContent) other).recipes)
                && ingredients.equals(((AppContent) other).ingredients));
    }

    @Override
    public int hashCode() {
        return recipes.hashCode();
    }
}
