package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.UniqueCarparkList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

//    private final UniquePersonList persons;
    private final UniqueCarparkList carparks;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        carparks = new UniqueCarparkList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the carpark list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCarparks(List<Carpark> carparks) {
        this.carparks.setCarparks(carparks);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCarparks(newData.getCarparkList());
    }

    //// carpark-level operations

    /**
     * Returns true if a carpark with the same identity as {@code carpark} exists in the address book.
     */
    public boolean hasCarpark(Carpark carpark) {
        requireNonNull(carpark);
        return carparks.contains(carpark);
    }

    /**
     * Adds a carpark to the address book.
     * The carpark must not already exist in the address book.
     */
    public void addPerson(Carpark c) {
        carparks.add(c);
    }

    /**
     * Replaces the given carpark {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The carpark identity of {@code editedPerson} must not be the same as another existing carpark in the address book.
     */
    public void updatePerson(Carpark target, Carpark editedPerson) {
        requireNonNull(editedPerson);

        carparks.setCarpark(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Carpark key) {
        carparks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return carparks.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Carpark> getCarparkList() {
        return carparks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && carparks.equals(((AddressBook) other).carparks));
    }

    @Override
    public int hashCode() {
        return carparks.hashCode();
    }
}
