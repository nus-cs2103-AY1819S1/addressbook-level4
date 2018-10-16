package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.person.Address;
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
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing
     * person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
    
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
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
    
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof AddressBook)) {
            return false;
        }
    
        AddressBook otherBook = (AddressBook) other;
        return persons.equals(otherBook.persons)
            && modules.equals(otherBook.modules)
            && occasions.equals(otherBook.occasions);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
        // TODO: proper design of getting the hashcode of this addressBook
    }
}
