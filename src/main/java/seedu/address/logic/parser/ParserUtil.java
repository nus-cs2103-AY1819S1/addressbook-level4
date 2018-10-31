package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //TODO: Left as an example, to be deleted/replaced.
    //    /**
    //     * Parses a {@code String tag} into a {@code Tag}.
    //     * Leading and trailing whitespaces will be trimmed.
    //     *
    //     * @throws ParseException if the given {@code tag} is invalid.
    //     */
    //    public static Tag parseTag(String tag) throws ParseException {
    //        requireNonNull(tag);
    //        String trimmedTag = tag.trim();
    //        if (!Tag.isValidTagName(trimmedTag)) {
    //            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
    //        }
    //        return new Tag(trimmedTag);
    //    }

    /**
     * Parses a {@code String filepath} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filepath} is invalid.
     */
    public static Path parseFilePath(String filepath) throws ParseException {
        requireNonNull(filepath);

        String trimmedFilePath = filepath.trim();
        Path dir;
        try {
            dir = Paths.get(trimmedFilePath);
        } catch (InvalidPathException e) {
            throw new ParseException("parsing fail");
        }

        if (dir.toString().equals("")) {
            throw new ParseException("Empty");
        }
        return dir;
    }
}
