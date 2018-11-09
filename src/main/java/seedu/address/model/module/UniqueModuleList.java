package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#equals(Module)}. As such, adding and updating of
 * modules uses Module#equals(Module) for equality so as to ensure that the module being added or updated is
 * unique in terms of identity in the UniqueModuleList.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();

    public UniqueModuleList(List<Module> moduleList) {
        for (Module module : moduleList) {
            internalList.add(module);
        }
    }

    public UniqueModuleList() {

    }

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Module moduleToCheck) {
        requireNonNull(moduleToCheck);
        return internalList.stream().anyMatch(moduleToCheck::equals);
    }

    /**
     * Adds the specified module to the list iff it is not originally contained
     * within it.
     */
    public void add(Module moduleToAdd) {
        requireNonNull(moduleToAdd);
        if (contains(moduleToAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(moduleToAdd);
    }

    /**
     * Makes an identical deep copy of this List.
     */
    public UniqueModuleList makeDeepDuplicate() {
        List<Module> newList = this.internalList.stream()
                                    .map(value -> value.makeDeepDuplicate()).collect(Collectors.toList());
        return new UniqueModuleList(newList);
    }

    /**
     * Makes an copy of this UniqueModuleList with shallow copies of the modules inside.
     */
    public UniqueModuleList makeShallowDuplicate() {
        List<Module> newList = this.internalList.stream()
                .map(value -> value.makeShallowDuplicate()).collect(Collectors.toList());
        return new UniqueModuleList(newList);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module in the list.
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
     * Removes the designated module from the internal list.
     */
    public void remove(Module moduleToRemove) {
        requireNonNull(moduleToRemove);
        if (!internalList.remove(moduleToRemove)) {
            throw new DuplicateModuleException();
        }
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setModules(List<Module> modules) {
        requireNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns the backing list as a normal modifiable list {@code List}.
     */
    public List<Module> asNormalList() {
        return internalList.stream().collect(Collectors.toList());
    }
    
    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size(); i++) {
            for (int j = 0; j < modules.size(); j++) {
                if (i != j) {
                    if (modules.get(i).equals(modules.get(j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
