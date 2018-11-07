package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's Title in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidTitle(String)}
 *
 * @author waytan
 */
public class ModuleTitle {

    public static final String MESSAGE_MODULETITLE_CONSTRAINTS =
            "Module Titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The title should consist of any number of alphanumeric characters and spaces.
     */
    public static final String MODULETITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullModuleTitle;

    /**
     * Empty constructor.
     */
    public ModuleTitle() {
        fullModuleTitle = "";
    }

    /**
     * Constructs a {@code ModuleTitle}.
     *
     * @param title A valid module Title.
     */
    public ModuleTitle(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_MODULETITLE_CONSTRAINTS);
        fullModuleTitle = title;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(MODULETITLE_VALIDATION_REGEX);
    }

    /**
     * Makes an identical deep copy of this ModuleTitle.
     */
    public ModuleTitle makeDeepDuplicate() {
        ModuleTitle newTitle = new ModuleTitle(new String(fullModuleTitle));
        return newTitle;
    }

    @Override
    public String toString() {
        return fullModuleTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleTitle // instanceof handles nulls
                && fullModuleTitle.equals(((ModuleTitle) other).fullModuleTitle)); // state check
    }

    @Override
    public int hashCode() {
        return fullModuleTitle.hashCode();
    }
}
