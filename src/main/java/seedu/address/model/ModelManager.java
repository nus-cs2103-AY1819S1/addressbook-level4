package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.article.Article;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Article> filteredArticles;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredArticles = new FilteredList<>(versionedAddressBook.getArticleList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasArticle(Article article) {
        requireNonNull(article);
        return versionedAddressBook.hasArticle(article);
    }

    @Override
    public void deleteArticle(Article target) {
        versionedAddressBook.removeArticle(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addArticle(Article article) {
        versionedAddressBook.addArticle(article);
        updateFilteredArticleList(PREDICATE_SHOW_ALL_ARTICLES);
        indicateAddressBookChanged();
    }

    @Override
    public void updateArticle(Article target, Article editedArticle) {
        requireAllNonNull(target, editedArticle);

        versionedAddressBook.updateArticle(target, editedArticle);
        indicateAddressBookChanged();
    }

    //=========== Filtered Article List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Article} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Article> getFilteredArticleList() {
        return FXCollections.unmodifiableObservableList(filteredArticles);
    }

    @Override
    public void updateFilteredArticleList(Predicate<Article> predicate) {
        requireNonNull(predicate);
        filteredArticles.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredArticles.equals(other.filteredArticles);
    }

}
