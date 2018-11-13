package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.module.exceptions.MultipleModuleEntryFoundException;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    // TODO: REMOVE LEGACY CODE
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    //@@author alexkmj
    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Clears existing backing model and replaces with the newly provided data.
     *
     * @param replacement the replacement.
     */
    void resetData(ReadOnlyTranscript replacement);

    // TODO: REMOVE LEGACY CODE
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the Transcript.
     *
     * @return read only version of the transcript
     */
    ReadOnlyTranscript getTranscript();

    /**
     * Returns true if a module with the same identity as {@code module} exists
     * in the transcript.
     *
     * @param module module to find in the transcript
     * @return true if module exists in transcript
     */
    boolean hasModule(Module module);

    /**
     * Returns the matching module entry.
     * <p>
     * Finds the module with {@code targetCode}, {@code targetYear} if
     * {@code targetYear} is not null, and {@code targetSemester} if
     * {@code targetSemester} is not null.
     *
     * @param targetCode code to match
     * @param targetYear year to match if not null
     * @param targetSemester semester to match if not null
     * @return the matching module
     * @throws ModuleNotFoundException thrown when no entries match the
     * parameters.
     * @throws MultipleModuleEntryFoundException thrown when multiple entries
     * match the parameters.
     */
    Module getOnlyOneModule(Code targetCode, Year targetYear,
            Semester targetSemester)
            throws ModuleNotFoundException, MultipleModuleEntryFoundException;

    /**
     * Adds the given module.
     * <p>
     * {@code module} must not already exist in the transcript.
     *
     * @param module module to be added into the transcript
     */
    void addModule(Module module);

    /**
     * Deletes the given module.
     * <p>
     * The module must exist in the transcript.
     *
     * @param target module to be deleted from the transcript
     */
    void deleteModule(Module target);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the transcript. The module identity of
     * {@code editedModule} must not be the same as another existing module in
     * the transcript.
     *
     * @param target module to be updated
     * @param editedModule the updated version of the module
     */
    void updateModule(Module target, Module editedModule);

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the
     * internal list of {@code versionedTranscript}
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given
     * {@code predicate}.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns true if the model has previous transcript states to restore.
     *
     * @return true if transcript can be undone
     */
    boolean canUndoTranscript();

    /**
     * Returns true if the model has undone transcript states to restore.
     *
     * @return true if transcript can be redone
     */
    boolean canRedoTranscript();

    /**
     * Restores the model's transcript to its previous state.
     */
    void undoTranscript();

    /**
     * Restores the model's transcript to its previously undone state.
     */
    void redoTranscript();

    /**
     * Saves the current transcript state for undo/redo.
     */
    void commitTranscript();
    //@@author

    //@@author jeremiah-ang
    /**
     * Get the cap goal of the current transcript.
     */
    CapGoal getCapGoal();

    /**
     * Set the cap goal of the current transcript.
     */
    void updateCapGoal(double capGoal);

    /**
     * Returns the CAP based on the current Transcript records.
     */
    double getCap();

    /**
     * Returns an unmodifiable view of list of modules that have completed.
     *
     * @return completed module list
     */
    ObservableList<Module> getCompletedModuleList();

    /**
     * Returns an unmodifiable view of list of modules that have yet been
     * completed.
     *
     * @return incomplete module list
     */
    ObservableList<Module> getIncompleteModuleList();

    /**
     * Adjust the target Module to the desired Grade
     * @param targetModule
     * @param adjustGrade
     * @return adjusted Module
     */
    Module adjustModule(Module targetModule, Grade adjustGrade);
    //@@author

    // TODO: REMOVE LEGACY CODE

    ReadOnlyAddressBook getAddressBook();

    boolean hasPerson(Person person);

    void deletePerson(Person target);

    void addPerson(Person person);

    void updatePerson(Person target, Person editedPerson);

    ObservableList<Person> getFilteredPersonList();

    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean canUndoAddressBook();

    boolean canRedoAddressBook();

    void undoAddressBook();

    void redoAddressBook();

    void commitAddressBook();
}
