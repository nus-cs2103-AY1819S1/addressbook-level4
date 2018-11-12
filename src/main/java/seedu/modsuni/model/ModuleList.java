package seedu.modsuni.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.UniqueModuleList;

/**
 * Wraps all data relating to modules
 */
public class ModuleList implements ReadOnlyModuleList {

    private final UniqueModuleList modules;

    public ModuleList() {
        modules = new UniqueModuleList();
    }

    public ModuleList(ReadOnlyModuleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyModuleList newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    //// module-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the modsuni book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the module list.
     * The module must not already exist in the module list.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the module list.
     * The module identity of {@code editedModule} must not be the same as another existing module in the
     * module list.
     */
    public void updateModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code ModuleList}.
     * {@code key} must exist in the modsuni book.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    //// util methods

    /**
     * Searches {@code code} inside this {@code ModuleList}.
     * Returns an optional of module searched by {@code code}.
     */
    public Optional<Module> searchCode(Code code) {
        requireNonNull(code);
        return modules.search(code);
    }

    /**
     * Searches the Index of {@Code toSearch} in this {@Code ModuleList}
     * Returns the Index of {@Code toSearch}
     */
    public Index searchForIndex(Module module) {
        requireNonNull(module);
        return modules.searchForIndex(module);
    }

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleList // instanceof handles nulls
                && modules.equals(((ModuleList) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
