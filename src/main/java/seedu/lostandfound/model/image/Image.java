package seedu.lostandfound.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.AppUtil.checkArgument;

/**
 * Represents a Article's image in the article list.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Image {

    public static final String MESSAGE_CONSTRAINTS = "Path should be valid";

    public final String path;

    /**
     * Constructs a {@code Image}.
     *
     * @param path a valid path.
     */
    public Image(String path) {
        requireNonNull(path);
        checkArgument(isValid(path), MESSAGE_CONSTRAINTS);
        this.path = path;
    }

    /**
     * Returns true if the given string is a valid image path.
     */
    public static boolean isValid(String path) {
        //TODO: check if the path exist
        return true;
    }


    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && path.equals(((Image) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
