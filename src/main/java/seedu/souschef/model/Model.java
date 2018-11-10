package seedu.souschef.model;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;


/**
 * The API of the Model component.
 */
public interface Model<T extends UniqueType> {
    /** {@code Predicate} that always evaluate to true */

    Predicate<UniqueType> PREDICATE_SHOW_ALL = unused -> true;

    /** Returns the AppContent */
    ReadOnlyAppContent getAppContent();

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the application content.
     */
    boolean has(T t);

    /**
     * Deletes the given recipe.
     * The recipe must exist in the application content.
     */
    void delete(T target);

    /**
     * Adds the given recipe.
     * {@code recipe} must not already exist in the application content.
     */
    void add(T target);

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     * {@code target} must exist in the application content.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the application
     * content.
     */
    void update(T target, T edited);

    /**
     * Resets data in the UniqueList.
     */
    void resetList();

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<T> getFilteredList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredList(Predicate<? extends UniqueType> predicate);

    /**
     * Sorts the list using a given comparator.
     * @param comparator
     */
    void sort(Comparator<T> comparator);

    /**
     * Returns true if the recipeModel has previous application content states to restore.
     */
    boolean canUndoAppContent();

    /**
     * Returns true if the recipeModel has undone application content states to restore.
     */
    boolean canRedoAppContent();

    /**
     * Restores the recipeModel's application content to its previous state.
     */
    void undoAppContent();

    /**
     * Restores the recipeModel's application content to its previously undone state.
     */
    void redoAppContent();

    /**
     * Saves the current application content state for undo/redo.
     */
    void commitAppContent();
}
