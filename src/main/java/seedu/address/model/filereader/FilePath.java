package seedu.address.model.filereader;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;

/**
 * Represents a file path.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class FilePath {

    public static final String MESSAGE_FILEPATH_CONSTRAINTS =
            "File not found, path should be the absolute path of a csv file, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String filePath;

    /**
     * Constructs a {@code FilePath}.
     *
     * @param filePath A valid file path.
     */
    public FilePath(String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidPath(filePath), MESSAGE_FILEPATH_CONSTRAINTS);
        this.filePath = filePath;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPath(String test) {
        File contactsFile = new File(test);
        return contactsFile.isAbsolute() && contactsFile.isFile();
    }


    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilePath // instanceof handles nulls
                && filePath.equals(((FilePath) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

}
