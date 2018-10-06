package seedu.souschef.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.souschef.model.recipe.Recipe;

/**
 * Wraps all data at the application
 * content level
 * Duplicates are not allowed (by .isSame comparison)
 */
public class AppContent implements ReadOnlyAppContent {

    private final UniqueList<Recipe> recipes;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recipes = new UniqueList<>();
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
     * Replaces the contents of the recipe list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes.set(recipes);
    }

    /**
     * Resets the existing data of this {@code AppContent} with {@code newData}.
     */
    public void resetData(ReadOnlyAppContent newData) {
        requireNonNull(newData);

        setRecipes(newData.getRecipeList());
    }

    //// recipe-level operations

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the application content.
     */
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipes.contains(recipe);
    }

    /**
     * Adds a recipe to the application content.
     * The recipe must not already exist in the application content.
     */
    public void addRecipe(Recipe p) {
        recipes.add(p);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the application content.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the application
     * content.
     */
    public void updateRecipe(Recipe target, Recipe editedRecipe) {
        requireNonNull(editedRecipe);

        recipes.set(target, editedRecipe);
    }

    /**
     * Removes {@code key} from this {@code AppContent}.
     * {@code key} must exist in the application content.
     */
    public void removeRecipe(Recipe key) {
        recipes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return recipes.asUnmodifiableObservableList().size() + " recipes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recipe> getRecipeList() {
        return recipes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppContent // instanceof handles nulls
                && recipes.equals(((AppContent) other).recipes));
    }

    @Override
    public int hashCode() {
        return recipes.hashCode();
    }
}
