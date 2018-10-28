package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

//@@author denzelchung
/**
 * Represents a Person's picture in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPicture(String)}
 */
public class Picture {

    // https://www.stubbornjava.com/posts/reading-file-resources-with-guava
    public static final URL DEFAULT_PICTURE_URL = com.google.common.io.Resources
        .getResource("images/placeholder_image.jpg");
    public static final String MESSAGE_PICTURE_CONSTRAINTS =
        "Picture should be a valid file path";

    /*
     * Regular expression validation for path.
     */
    public static final String PICTURE_PATH_WIN_VALIDATION_REGEX =
        "([a-zA-Z]:)?((\\\\|/)[a-zA-Z0-9_.-]+)+(\\\\|/)?";
    public static final String DEFAULT_PATH_WIN_VALIDATION_REGEX =
        "/" + PICTURE_PATH_WIN_VALIDATION_REGEX;
    public static final String PICTURE_PATH_MAC_VALIDATION_REGEX =
        "^((?!.*//.*)(?!.*/ .*)/{1}([^\\\\(){}:\\*\\?<>\\|\\\"\\'])+\\.(jpg|png))$";

    public final String picture;

    /**
     * Constructs an {@code Picture}.
     *
     * @param picture A valid picture.
     */
    public Picture(String picture) {
        requireNonNull(picture);

        if (!isValidPicture(picture)) {
            this.picture = DEFAULT_PICTURE_URL.getPath();
        } else {
            this.picture = picture;
        }
    }

    /**
     * Returns the current directory concatenated with the given path.
     */
    public static String getDirectoryPath(String path) {
        return System.getProperty("user.dir") + "/" + path;
    }

    /**
     * Returns true if a given string is a valid picture.
     */
    public static boolean isValidPicture(String test) {
        if (test.matches(DEFAULT_PATH_WIN_VALIDATION_REGEX)) {
            test = test.substring(1, test.length());
        }
        return ((test.matches(PICTURE_PATH_WIN_VALIDATION_REGEX) || test.matches(PICTURE_PATH_MAC_VALIDATION_REGEX))
            && Files.exists(Paths.get(test)));
    }

    /**
     * Returns true if a given string is a valid picture in the current directory.
     */
    public static boolean isValidPictureInDirectory(String test) {
        String directoryPath = getDirectoryPath(test);
        return Files.exists(Paths.get(directoryPath));
    }

    @Override
    public String toString() {
        return picture;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Picture // instanceof handles nulls
            && picture.equals(((Picture) other).picture)); // state check
    }

    @Override
    public int hashCode() {
        return picture.hashCode();
    }

}
