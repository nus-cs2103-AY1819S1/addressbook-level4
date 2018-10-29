package seedu.thanepark.model;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.thanepark.commons.core.ComponentManager;
import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.commons.events.model.ThaneParkChangedEvent;
import seedu.thanepark.model.ride.Ride;

/**
 * Represents the in-memory model of the thanepark book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedThanePark versionedThanePark;
    private final FilteredList<Ride> filteredRides;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyThanePark addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with thanepark book: " + addressBook + " and user prefs " + userPrefs);

        versionedThanePark = new VersionedThanePark(addressBook);
        filteredRides = new FilteredList<>(versionedThanePark.getRideList());
    }

    public ModelManager() {
        this(new ThanePark(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyThanePark newData) {
        versionedThanePark.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyThanePark getThanePark() {
        return versionedThanePark;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ThaneParkChangedEvent(versionedThanePark));
    }

    @Override
    public boolean hasRide(Ride ride) {
        requireNonNull(ride);
        return versionedThanePark.hasRide(ride);
    }

    @Override
    public void deleteRide(Ride target) {
        versionedThanePark.removeRide(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addRide(Ride ride) {
        versionedThanePark.addRide(ride);
        updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        indicateAddressBookChanged();
    }

    @Override
    public void updateRide(Ride target, Ride editedRide) {
        requireAllNonNull(target, editedRide);

        versionedThanePark.updateRide(target, editedRide);
        indicateAddressBookChanged();
    }

    //=========== Filtered Ride List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Ride} backed by the internal list of
     * {@code versionedThanePark}
     */
    @Override
    public ObservableList<Ride> getFilteredRideList() {
        return FXCollections.unmodifiableObservableList(filteredRides);
    }

    @Override
    public void updateFilteredRideList(Predicate<Ride> predicate) {
        requireNonNull(predicate);
        filteredRides.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoThanePark() {
        return versionedThanePark.canUndo();
    }

    @Override
    public boolean canRedoThanePark() {
        return versionedThanePark.canRedo();
    }

    @Override
    public void undoThanePark() {
        versionedThanePark.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoThanePark() {
        versionedThanePark.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitThanePark() {
        versionedThanePark.commit();
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
        return versionedThanePark.equals(other.versionedThanePark)
                && filteredRides.equals(other.filteredRides);
    }

}
