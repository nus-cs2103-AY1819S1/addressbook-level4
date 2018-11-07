package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
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
        persons = new UniquePersonList(new ArrayList<>());
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


    /**
     * Returns true if an entity with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if an entity with the same identity as {@code occasion} exists in the address book.
     */
    public boolean hasOccasion(Occasion occasion) {
        requireNonNull(occasion);
        return occasions.contains(occasion);
    }

    /**
     * Returns true if an entity with the same identity as {@code module} exists in the address book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person person) {
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person
     * in the address book.
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

    /**
     * Adds an occasion to the address book.
     * The person must not already exist in the address book.
     */
    public void addOccasion(Occasion occasion) {
        occasions.add(occasion);
    }

    /**
     * Replaces the given occasion {@code target} in the list with {@code editedOccasion}.
     * {@code target} must exist in the address book.
     * The occasion identity of {@code editedOccasion} must not be the same as another
     * existing occasion in the address book.
     */
    public void updateOccasion(Occasion target, Occasion editedOccasion) {
        requireNonNull(editedOccasion);

        occasions.setOccasion(target, editedOccasion);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeOccasion(Occasion key) {
        occasions.remove(key);
    }

    /**
     * Adds a module to the address book.
     * The module must not already exist in the address book.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The module identity of {@code editedModule} must not be the same as another existing
     * module in the address book.
     */
    public void updateModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    public void insertPerson(Person personToInsert, Module moduleToInsert,
                             Person personToReplace, Module moduleToReplace) {
        requireAllNonNull(personToInsert, moduleToInsert);
        personToInsert.getModuleList().add(moduleToInsert);
        moduleToInsert.getStudents().add(personToInsert);
        updatePerson(personToReplace, personToInsert);
        updateModule(moduleToReplace, moduleToInsert);
    }

    public void insertPerson(Person personToInsert, Occasion occasionToInsert,
                             Person personToReplace, Occasion occasionToReplace) {
        requireAllNonNull(personToInsert, occasionToInsert, personToReplace, occasionToReplace);
        personToInsert.getOccasionList().add(occasionToInsert);
        occasionToInsert.getAttendanceList().add(personToInsert);
        updatePerson(personToReplace, personToInsert);
        updateOccasion(occasionToReplace, occasionToInsert);
    }


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
        return Objects.hash(persons, modules, occasions);
    }
}
