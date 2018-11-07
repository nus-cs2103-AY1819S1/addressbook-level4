package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's code in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidCode(String)}
 *
 * @author waytan
 */
public class ModuleCode {

    public static final String MESSAGE_MODULECODE_CONSTRAINTS =
            "Module codes should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The code must consist of 2 or 3 letters, followed by 4 digits and an optional letter behind.
     */
    public static final String MODULECODE_VALIDATION_REGEX =
            "[a-zA-Z]{2,}[0-9]{4}[a-zA-Z]*";

    public final String fullModuleCode;

    /**
     * Empty constructor.
     */
    public ModuleCode() {
        fullModuleCode = "";
    }
    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param code A valid module code.
     */
    public ModuleCode(String code) {
        requireNonNull(code);
        checkArgument(isValidCode(code), MESSAGE_MODULECODE_CONSTRAINTS);
        fullModuleCode = code;
    }

    public ModuleCode makeDeepDuplicate() {
        ModuleCode newCode = new ModuleCode(new String(fullModuleCode));
        return newCode;
    }

    /**
     * Returns true if a given string is a valid code.
     */
    public static boolean isValidCode(String test) {
        return test.matches(MODULECODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullModuleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && fullModuleCode.equals(((ModuleCode) other).fullModuleCode)); // state check
    }

    @Override
    public int hashCode() {
        return fullModuleCode.hashCode();
    }

}
