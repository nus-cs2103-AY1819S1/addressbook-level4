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
import seedu.address.commons.events.model.SsenisubChangedEvent;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of Ssenisub data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedSsenisub versionedSsenisub;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given Ssenisub and userPrefs.
     */
    public ModelManager(ReadOnlySsenisub ssenisub, UserPrefs userPrefs) {
        super();
        requireAllNonNull(ssenisub, userPrefs);

        logger.fine("Initializing with SSENISUB: " + ssenisub + " and user prefs " + userPrefs);

        versionedSsenisub = new VersionedSsenisub(ssenisub);
        filteredPersons = new FilteredList<>(versionedSsenisub.getPersonList());
    }

    public ModelManager() {
        this(new Ssenisub(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlySsenisub newData) {
        versionedSsenisub.resetData(newData);
        indicateSsenisubChanged();
    }

    @Override
    public ReadOnlySsenisub getSsenisub() {
        return versionedSsenisub;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateSsenisubChanged() {
        raise(new SsenisubChangedEvent(versionedSsenisub));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedSsenisub.hasPerson(person);
    }

    @Override
    public boolean hasName(Person person) {
        requireNonNull(person);
        return versionedSsenisub.hasName(person);
    }
    @Override
    public boolean hasPhoneNumber(Person person) {
        requireNonNull(person);
        return versionedSsenisub.hasPhoneNumber(person);
    }

    @Override
    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return versionedSsenisub.hasEmail(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedSsenisub.removePerson(target);
        indicateSsenisubChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedSsenisub.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateSsenisubChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedSsenisub.updatePerson(target, editedPerson);
        indicateSsenisubChanged();
    }

    @Override
    public void favouritePerson(Person target, Person favouritedPerson) {
        requireAllNonNull(target, favouritedPerson);

        versionedSsenisub.favouritePerson(target, favouritedPerson);
        indicateSsenisubChanged();
    }

    @Override
    public void unfavouritePerson(Person target, Person unfavouritedPerson) {
        requireAllNonNull(target, unfavouritedPerson);

        versionedSsenisub.favouritePerson(target, unfavouritedPerson);
        indicateSsenisubChanged();
    }

    @Override
    public void sortByName() {
        versionedSsenisub.sortByName();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateSsenisubChanged();
    }

    @Override
    public void sortByDept() {
        versionedSsenisub.sortByDept();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateSsenisubChanged();
    }

    @Override
    public void sortByRatingUp() {
        versionedSsenisub.sortByRatingUp();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateSsenisubChanged();
    }

    @Override
    public void sortByRatingDown() {
        versionedSsenisub.sortByRatingDown();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateSsenisubChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedSsenisub}
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
    public boolean canUndoSsenisub() {
        return versionedSsenisub.canUndo();
    }

    @Override
    public boolean canRedoSsenisub() {
        return versionedSsenisub.canRedo();
    }

    @Override
    public void undoSsenisub() {
        versionedSsenisub.undo();
        indicateSsenisubChanged();
    }

    @Override
    public void redoSsenisub() {
        versionedSsenisub.redo();
        indicateSsenisubChanged();
    }

    @Override
    public void commitSsenisub() {
        versionedSsenisub.commit();
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
        return versionedSsenisub.equals(other.versionedSsenisub)
                && filteredPersons.equals(other.filteredPersons);
    }

}
