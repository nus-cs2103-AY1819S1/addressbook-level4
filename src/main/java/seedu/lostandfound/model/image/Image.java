package seedu.lostandfound.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

import seedu.lostandfound.commons.util.FileUtil;
import seedu.lostandfound.model.util.Sequence;

/**
 * Represents a Article's image in the article list.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Image {

    private static final String VALIDATION_REGEX = "(0|[1-9][0-9]*)\\.(png|svg|jpg)";
    private static final Sequence SEQUENCE = Sequence.getInstance();
    private static final Path IMAGE_FOLDER = Paths.get("data", "images");

    public static final String MESSAGE_CONSTRAINTS = "Path should be valid";
    public static final Image DEFAULT = new Image("data/images/0.png");

    public String filename;
    private Path path;
    private String basename;
    private Integer id;

    /**
     * Constructs a {@code Image}.
     *
     * @param file a valid image file. will set a reference to that path.
     */
    public Image(String file) throws InvalidPathException {
        requireNonNull(file);
        checkArgument(isValid(file), MESSAGE_CONSTRAINTS);
        path = Paths.get(file);
        filename = FileUtil.getFilename(path);
        basename = FileUtil.getBasename(path);
        id = Integer.parseInt(basename);
        SEQUENCE.set(id);
    }

    /**
     * Constructs a {@code Image}
     *
     * @param file a valid image file. will set a reference to that path.
     */
    public Image(Path file) throws InvalidPathException {
        this(file.toString());
    }

    public static Image create(String pathName) throws IOException {
        return Image.create(Paths.get(pathName));
    }

    public static Image create(Path path) throws IOException {
        Path target = Paths.get(IMAGE_FOLDER.toString() , SEQUENCE.next().toString() + "." + FileUtil.getExtension(path));
        System.out.println(path);
        System.out.println(target);
        FileUtil.copy(path, target);
        return new Image(target);
    }

    /**
     * Returns true if the given string is a valid image path.
     */
    public static boolean isValid(String test) {
        if (test == null) {
            return false;
        }

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
