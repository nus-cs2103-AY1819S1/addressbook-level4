package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.module.exceptions.MultipleModuleEntryFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not
 * allow nulls.
 * <p>
 * A module is considered unique by comparing
 * {@code moduleA.isSameModule(moduleB)}.
 * <p>
 * As such, adding and updating of modules uses {@code moduleA.equals(moduleB)}
 * for equality so as to ensure that the module being added or updated is unique
 * in terms of identity in the {@code UniqueModuleList}.
 */
public class UniqueModuleList implements Iterable<Module> {
    //@@author alexkmj
    /**
     * Creates an observable list of module.
     * See {@link Module}.
     */
    private final ObservableList<Module> internalList =
            FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent module as the given
     * argument. See {@link Module}.
     *
     * @param toCheck the module that is being checked against
     * @return true if list contains equivalent module
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a module to the list.
     * <p>
     * The {@link Module} should not exist in the list.
     *
     * @param toAdd the module that would be added into the list
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }
    //@@author

    //@@author jeremiah-ang
    /**
     * Adds all module in a list to the list.
     *
     * @param modules collection of modules to add
     * @return true if this list changed as a result of the call
     */
    public boolean addAll(Collection<Module> modules) {
        return internalList.addAll(modules);
    }
    //@@author

    //@@author alexkmj
    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * <p>
     * {@code target} must exist in the list. The {@link Module} identity of
     * {@code editedModule} must not be the same as another existing module in
     * the list.
     *
     * @param target the module to be replaced
     * @param editedModule the modue that replaces the old module
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.isSameModule(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    /**
     * Replaces the {@link #internalList} of this {@code UniqueModuleList} with
     * the {@code internalList} of the replacement.
     *
     * @param replacement the {@code UniqueModuleList} object that contains the
     * {@code internalList} that is replacing the old {@code internalList}
     */
    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * <p>
     * {@code modules} must not contain duplicate modules.
     *
     * @param modules the list of module that would replace the old list
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Removes the equivalent module from the list.
     * <p>
     * The {@link Module} must exist in the list.
     *
     * @param module the code that the module to be removed contains
     */
    public void remove(Module module) {
        requireNonNull(module);

        if (!internalList.remove(module)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Removes the equivalent module from the list.
     * <p>
     * The {@link Module} must exist in the list.
     *
     * @param filter the predicate used to filter the modules to be removed
     */
    public void remove(Predicate<Module> filter) {
        boolean successful = internalList.removeIf(filter);

        if (!successful) {
            throw new ModuleNotFoundException();
        }
    }
    //@@author

    //@@author jeremiah-ang
    /**
     * Removes all module in a list from the list
     */
    public boolean removeAll(Collection<Module> modules) {
        return internalList.removeAll(modules);
    }
    //@@author

    //@@author alexkmj
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return backing list as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }
    //@@author

    /**
     * Returns true if {@code modules} contains only unique modules.
     *
     * @param modules the module list that is being checked
     * @return true if modules are unique and false if modules are not unique
     */
    private boolean modulesAreUnique(List<Module> modules) {
        return modules.size() == modules.parallelStream()
                .distinct()
                .count();
    }
    //@@author

    //@@author jeremiah-ang
    /**
     * Returns the list of filtered Module based on the given predicate
     *
     * @param predicate
     * @return filtered list
     */
    public ObservableList<Module> getFilteredModules(Predicate<Module> predicate) {
        return internalList.filtered(predicate);
    }

    /**
     * Finds Module that isSameModule as moduleToFind
     * @param moduleToFind
     * @return the Module that matches; null if not matched
     */
    public Module find(Module moduleToFind) throws ModuleNotFoundException {
        return internalList.stream()
                .filter(index -> index.isSameModule(moduleToFind))
                .findAny()
                .orElseThrow(() -> new ModuleNotFoundException());
    }
    //@@author

    //@@author alexkmj
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
    public Module getOnlyOneModule(Code targetCode, Year targetYear,
            Semester targetSemester)
            throws ModuleNotFoundException, MultipleModuleEntryFoundException {
        requireNonNull(targetCode);

        Predicate<Module> moduleMatches = index -> {
            boolean codeMatch = targetCode == null
                    || index.getCode().equals(targetCode);

            boolean yearMatch = targetYear == null
                    || index.getYear().equals(targetYear);

            boolean semesterMatch = targetSemester == null
                    || index.getSemester().equals(targetSemester);

            return codeMatch && yearMatch && semesterMatch;
        };

        Module[] moduleArray = internalList.stream()
                .filter(moduleMatches)
                .toArray(Module[]::new);

        if (moduleArray.length == 0) {
            throw new ModuleNotFoundException();
        }

        if (moduleArray.length > 1) {
            throw new MultipleModuleEntryFoundException();
        }

        return moduleArray[0];
    }

    /**
     * Returns the iterator of the internal list.
     *
     * @return iterator of the internal list
     */
    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    /**
     * Compares the internal list of both UniqueModuleList object.
     * <p>
     * This defines a notion of equality between two UniqueModuleList objects.
     *
     * @param other Code object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UniqueModuleList)) {
            return false;
        }

        UniqueModuleList e = (UniqueModuleList) other;
        return internalList.equals(e.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    //@@author
}
