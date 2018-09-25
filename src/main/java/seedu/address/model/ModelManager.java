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
import seedu.address.commons.events.model.WishBookChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedWishBook versionedWishBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given wishBook and userPrefs.
     */
    public ModelManager(ReadOnlyWishBook wishBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(wishBook, userPrefs);

        logger.fine("Initializing with address book: " + wishBook + " and user prefs " + userPrefs);

        versionedWishBook = new VersionedWishBook(wishBook);
        filteredPersons = new FilteredList<>(versionedWishBook.getWishList());
    }

    public ModelManager() {
        this(new WishBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyWishBook newData) {
        versionedWishBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyWishBook getWishBook() {
        return versionedWishBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new WishBookChangedEvent(versionedWishBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedWishBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedWishBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedWishBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedWishBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    /**
     * Removes {@code tag} from all {@code person}s in this {@code WishBook}.
     * @throws DuplicatePersonException if there's a duplicate {@code Person} in this {@code WishBook}.
     */
    public void deleteTag(Tag tag) throws DuplicatePersonException {

        versionedWishBook.removeTagFromAll(tag);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedWishBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoWishBook() {
        return versionedWishBook.canUndo();
    }

    @Override
    public boolean canRedoWishBook() {
        return versionedWishBook.canRedo();
    }

    @Override
    public void undoWishBook() {
        versionedWishBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoWishBook() {
        versionedWishBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitWishBook() {
        versionedWishBook.commit();
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
        return versionedWishBook.equals(other.versionedWishBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
