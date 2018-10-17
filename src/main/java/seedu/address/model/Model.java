package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Issue> PREDICATE_SHOW_ALL_ISSUES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySaveIt newData);

    /** Reset the current directory. */
    void resetDirectory(Index targetIndex, boolean rootDirectory);

    /** Return the current directory. */
    int getCurrentDirectory();

    /** Returns the SaveIt */
    ReadOnlySaveIt getSaveIt();

    /**
     * Returns true if an issue with the same identity as {@code issue} exists in the saveIt.
     */
    boolean hasIssue(Issue issue);

    /**
     * Deletes the given issue.
     * The issue must exist in the saveIt.
     */
    void deleteIssue(Issue target);

    /**
     * Adds the given issue.
     * {@code issue} must not already exist in the saveIt.
     */
    void addIssue(Issue issue);

    /**
     * Replaces the given issue {@code target} with {@code editedIssue}.
     * {@code target} must exist in the saveIt.
     * The issue identity of {@code editedIssue} must not be the same as another existing issue in the saveIt.
     */
    void updateIssue(Issue target, Issue editedIssue);

    /** Returns an unmodifiable view of the filtered issue list */
    ObservableList<Issue> getFilteredIssueList();

    /**
     * Filters the issues given the predicate and sorts them based on the search frequency
     */
    void filterIssues(Predicate<Issue> predicate);

    /**
     * Updates the filter of the filtered issue list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIssueList(Predicate<Issue> predicate);

    /**
     * Returns true if the model has previous saveIt states to restore.
     */
    boolean canUndoSaveIt();

    /**
     * Returns true if the model has undone saveIt states to restore.
     */
    boolean canRedoSaveIt();

    /**
     * Restores the model's saveIt to its previous state.
     */
    void undoSaveIt();

    /**
     * Restores the model's saveIt to its previously undone state.
     */
    void redoSaveIt();

    /**
     * Saves the current saveIt state for undo/redo.
     */
    void commitSaveIt();
}
