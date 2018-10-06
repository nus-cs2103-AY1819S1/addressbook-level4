package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.article.Article;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Article> PREDICATE_SHOW_ALL_ARTICLES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a article with the same identity as {@code article} exists in the address book.
     */
    boolean hasArticle(Article article);

    /**
     * Deletes the given article.
     * The article must exist in the address book.
     */
    void deleteArticle(Article target);

    /**
     * Adds the given article.
     * {@code article} must not already exist in the address book.
     */
    void addArticle(Article article);

    /**
     * Replaces the given article {@code target} with {@code editedArticle}.
     * {@code target} must exist in the address book.
     * The article identity of {@code editedArticle} must not be the same as another existing article in the address book.
     */
    void updateArticle(Article target, Article editedArticle);

    /** Returns an unmodifiable view of the filtered article list */
    ObservableList<Article> getFilteredArticleList();

    /**
     * Updates the filter of the filtered article list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArticleList(Predicate<Article> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}
