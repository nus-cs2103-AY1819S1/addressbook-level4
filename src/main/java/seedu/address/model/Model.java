package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.util.TypeUtil;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Occasion> PREDICATE_SHOW_ALL_OCCASIONS = unused -> true;
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the type of list that is currently active. */
    TypeUtil getActiveType();

    /** Changes the type of list that is currently active. */
    void setActiveType(TypeUtil newActiveType);

    /**
     * Returns true if an occasion with the same identity as {@code occasion} exists in the address book.
     */
    boolean hasOccasion(Occasion occasion);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(Module module);


    /**
     * Adds the given Occasion.
     * {@code occasion} must not already exist in the address book.
     */
    void addOccasion(Occasion occasion);

    /**
     * Adds the given Person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given Module.
     * {@code module} must not already exist in the address book.
     */
    void addModule(Module module);

    /**
     * Replaces the given entity {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replaces the given entity {@code target} with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    void updateModule(Module target, Module editedModule);

    /**
     * Replaces the given entity {@code target} with {@code editedOccasion}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedOccasion} must not be the same as another existing occasion in the address book.
     */
    void updateOccasion(Occasion target, Occasion editedOccasion);

    /**
     * Deletes the given Person.
     * {@code person} must exist in the address book.
     */
    void deletePerson(Person person);

    /**
     * Deletes the given Module.
     * {@code module} must exist in the address book.
     */
    void deleteModule(Module module);

    /**
     * Deletes the given Occasion.
     * {@code occasion} must exist in the address book.
     */
    void deleteOccasion(Occasion occasion);

    /** Returns an unmodifiable view of the filtered persons list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered modules list */
    ObservableList<Module> getFilteredModuleList();

    /** Returns an unmodifiable view of the filtered occasions list */
    ObservableList<Occasion> getFilteredOccasionList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOccasionList(Predicate<Occasion> predicate);

    /**
     * Inserts the person into the module bidirectionally.
     */
    void insertPerson(Person person, Module module, Person personToReplace, Module moduleToReplace);

    /**
     * Inserts the person into an occasion bidirectionally.
     */
    void insertPerson(Person person, Occasion occasion, Person personToReplace, Occasion occasionToReplace);

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
