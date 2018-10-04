package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Description;
import seedu.address.model.person.DueDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.PriorityValue;
import seedu.address.model.tag.Label;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String dueDate} into a {@code DueDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dueDate} is invalid.
     */
    public static DueDate parseDueDate(String dueDate) throws ParseException {
        requireNonNull(dueDate);
        String trimmedDueDate = dueDate.trim();
        if (!DueDate.isValidDueDate(trimmedDueDate)) {
            throw new ParseException(DueDate.MESSAGE_DUEDATE_CONSTRAINTS);
        }
        return new DueDate(trimmedDueDate);
    }

    /**
     * Parses a {@code String address} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Description parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Description.isValidDescription(trimmedAddress)) {
            throw new ParseException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code PriorityValue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static PriorityValue parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!PriorityValue.isValidPriorityValue(trimmedEmail)) {
            throw new ParseException(PriorityValue.MESSAGE_PRIORITY_VALUE_CONSTRAINTS);
        }
        return new PriorityValue(trimmedEmail);
    }

    /**
     * Parses a {@code String label} into a {@code Label}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code label} is invalid.
     */
    public static Label parseLabel(String label) throws ParseException {
        requireNonNull(label);
        String trimmedLabel = label.trim();
        if (!Label.isValidLabelName(trimmedLabel)) {
            throw new ParseException(Label.MESSAGE_LABEL_CONSTRAINTS);
        }
        return new Label(trimmedLabel);
    }

    /**
     * Parses {@code Collection<String> labels} into a {@code Set<Label>}.
     */
    public static Set<Label> parseLabels(Collection<String> labels) throws ParseException {
        requireNonNull(labels);
        final Set<Label> labelSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(parseLabel(labelName));
        }
        return labelSet;
    }
}
