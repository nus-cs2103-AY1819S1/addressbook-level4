package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Represents a Person's profile picture file path in the address book.
 */
//@@author javenseow
public class ProfilePicture {

    public static final String MESSAGE_PROFILE_PICTURE_CONSTRAINTS =
            "Profile picture should be either a .jpeg or .png file, and not empty";

    /*
     * The first character of the file path must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_-]+)+\\\\?";
    public final File filePath;

    /**
     * Constructs a {@code ProfilePicture}.
     *
     * @param path a valid file path.
     */
    public ProfilePicture(File path) {
        requireNonNull(path);
        //checkArgument(isValidProfilePicture(path), MESSAGE_PROFILE_PICTURE_CONSTRAINTS);
        filePath = path;
    }

    public File getPicture() {
        return filePath.getAbsoluteFile();
    }

    /**
     * Returns true if a given string ends with .jpeg or .png.
     */
    public static boolean isValidProfilePicture(File test) {
        return test.getAbsolutePath().matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return filePath.getAbsolutePath();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProfilePicture) // instanceof handles null
            && filePath.equals(((ProfilePicture) other).filePath);
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}
