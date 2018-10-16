package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.entity.Entity;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueModuleList modules;
    private final UniqueOccasionList occasions;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        modules = new UniqueModuleList();
        occasions = new UniqueOccasionList();
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
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the occasion list with {@code occasions}.
     * {@code occasions} must not contain duplicate occasions.
     */
    public void setOccasions(List<Occasion> occasions) {
        this.occasions.setOccasions(occasions);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setModules(newData.getModuleList());
        setOccasions(newData.getOccasionList());
    }

    //// person-level operation
    // TODO: Refactor it to Entity-level
    /**
     * Returns true if an entity with the same identity as {@code person} exists in the address book.
     */
    public boolean hasEntity(Entity entity) {
        requireNonNull(entity);
        return persons.contains(entity) || modules.contains(entity) || occasions.contains(entity);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addEntity(Entity entity) {
        persons.add(entity);
        modules.add(entity);
        occasions.add(entity);
    }

    /**
     * Replaces the given entity {@code target} in the list with {@code editedEntity}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedEntity} must not be the same as another existing entity in the address book.
     */

    public void updateEntity(Entity target, Entity editedEntity) {
        requireNonNull(editedEntity);

        persons.setEntity(target, editedEntity);
        modules.setEntity(target, editedEntity);
        occasions.setEntity(target, editedEntity);
    }

    /**
     * Removes {@code entity} from this {@code AddressBook}.
     * {@code entity} must exist in the address book.
     */
    public void removeEntity (Entity entity) {
        persons.remove(entity);
        modules.remove(entity);
        occasions.remove(entity);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons"
                + modules.asUnmodifiableObservableList().size() + " modules"
                + occasions.asUnmodifiableObservableList().size() + " occasions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    //@Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    //@Override
    public ObservableList<Occasion> getOccasionList() {
        return occasions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && modules.equals(((AddressBook) other).modules)
                && occasions.equals(((AddressBook) other).occasions));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
        // TODO: proper design of getting the hashcode of this addressBook
    }
}
