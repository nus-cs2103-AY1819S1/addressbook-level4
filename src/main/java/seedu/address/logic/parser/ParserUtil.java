package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code s} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed
     */
    public static String parseString(String s) {
        String string = s.trim();
        return string;
    }

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

    /**
     * Parses a {@code String category} into an {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategoryName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CATE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code s} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the fields are not in the valid form.
     */
    public static String parseEntryInfoFields(String s) throws ParseException {
        String trimmedfield = s.trim();
        if (!EntryInfo.isValidEntryInfoField(trimmedfield)) {
            throw new ParseException(EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS);
        }
        return trimmedfield;
    }

    /**
     *
     * Parses {@code String header} {@code String subHeader} {@code String duration} into an {@code EntryInfo}.
     * @throws ParseException
     */
    public static EntryInfo parseEntryInfo(String header, String subHeader, String duration) throws ParseException {
        List<String> entryInfoList = Arrays.asList(header, subHeader, duration);
        CollectionUtil.requireAllNonNull(entryInfoList);
        List<String> trimmedEntryInfoList = entryInfoList.stream()
                .map(s -> s.trim()).collect(Collectors.toList());

        if (!EntryInfo.isValidEntryInfo(trimmedEntryInfoList)) {
            throw new ParseException(EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS);
        }
        return new EntryInfo(trimmedEntryInfoList);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
