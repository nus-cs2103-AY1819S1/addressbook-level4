package seedu.souschef.model;

import static java.util.Objects.requireNonNull;

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
     * Resets the existing data of this {@code AppContent} with {@code newData}.
     */
    public void resetData(ReadOnlyAppContent newData) {
        requireNonNull(newData);
        this.recipes.set(newData.getObserableRecipeList());
    }

    //// recipe-level operations
    public UniqueList<Recipe> getRecipes() {
        return recipes;
    }

    //// util methods
    @Override
    public String toString() {
        return recipes.asUnmodifiableObservableList().size() + " recipes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recipe> getObserableRecipeList() {
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
