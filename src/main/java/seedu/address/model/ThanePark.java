package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.UniqueRideList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRide comparison)
 */
public class ThanePark implements ReadOnlyThanePark {

    private final UniqueRideList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueRideList();
    }

    public ThanePark() {}

    /**
     * Creates an ThanePark using the Persons in the {@code toBeCopied}
     */
    public ThanePark(ReadOnlyThanePark toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the ride list with {@code rides}.
     * {@code rides} must not contain duplicate rides.
     */
    public void setPersons(List<Ride> rides) {
        this.persons.setRides(rides);
    }

    /**
     * Resets the existing data of this {@code ThanePark} with {@code newData}.
     */
    public void resetData(ReadOnlyThanePark newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// ride-level operations

    /**
     * Returns true if a ride with the same identity as {@code ride} exists in the address book.
     */
    public boolean hasPerson(Ride ride) {
        requireNonNull(ride);
        return persons.contains(ride);
    }

    /**
     * Adds a ride to the address book.
     * The ride must not already exist in the address book.
     */
    public void addPerson(Ride p) {
        persons.add(p);
    }

    /**
     * Replaces the given ride {@code target} in the list with {@code editedRide}.
     * {@code target} must exist in the address book.
     * The ride identity of {@code editedRide} must not be the same as another existing ride in the address book.
     */
    public void updatePerson(Ride target, Ride editedRide) {
        requireNonNull(editedRide);

        persons.setRide(target, editedRide);
    }

    /**
     * Removes {@code key} from this {@code ThanePark}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Ride key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Ride> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThanePark // instanceof handles nulls
                && persons.equals(((ThanePark) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
