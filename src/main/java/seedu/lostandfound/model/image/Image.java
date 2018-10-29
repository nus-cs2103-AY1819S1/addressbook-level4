package seedu.lostandfound.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import seedu.lostandfound.commons.util.FileUtil;
import seedu.lostandfound.model.util.Sequence;

/**
 * Represents a Article's image in the article list.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Image {

    public static final String MESSAGE_CONSTRAINTS = "Path should be valid";
    private static final String VALIDATION_REGEX = "(0|[1-9][0-9]*)\\.(png|svg|jpg)";
    private static final Sequence SEQUENCE = Sequence.getInstance();

    private final Path path;
    private String filename;
    private String basename;
    private String extension;
    private Integer id;
    /**
     * Constructs a {@code Image}.
     *
     * @param path a valid path.
     */
    public Image(String path) {
        requireNonNull(path);
        checkArgument(isValid(path), MESSAGE_CONSTRAINTS);
        this.path = Paths.get(path);
        String[] splittedFilename = filename.split("\\.");
        this.basename = splittedFilename[0];
        this.extension = splittedFilename[1];
        this.id = Integer.parseInt(basename);
        SEQUENCE.set(id));
    }

    public String getFilename() {
        return this.filename;
    }

    /**
     * Returns true if the given string is a valid image path.
     */
    public static boolean isValid(String test) {
        if (!FileUtil.isValidPath(test)) {
            return false;
        }
        if (!FileUtil.isFileExists(Paths.get(test))) {
            return false;
        }
        return Paths.get(test).getFileName().toString().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return path.toString();
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
