package seedu.restaurant.logic.parser.menu;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.util.StringUtil;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;

//@@author yican95
/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ItemParserUtil {
    public static final String MESSAGE_NOT_INDEX_OR_NAME = "A valid index or name must be entered.";

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String price} into a {@code Price}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String percent} into a {@code double}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code percent} is invalid.
     */
    public static double parsePercent(String percent) throws ParseException {
        requireNonNull(percent);
        String trimmedPercent = percent.trim();
        if (!Price.isValidPercent(trimmedPercent)) {
            throw new ParseException(Price.MESSAGE_PERCENT_CONSTRAINTS);
        }
        return Double.parseDouble(trimmedPercent);
    }

    /**
     * Parses a {@code String indexOrName} into an {@code Index} or {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if neither the {@code Index} nor {@code Name} is valid.
     */
    public static Object parseIndexOrName(String indexOrName) throws ParseException {
        requireNonNull(indexOrName);
        String trimmedIndexOrName = indexOrName.trim();

        if (StringUtil.isNonZeroUnsignedInteger(trimmedIndexOrName)) {
            return Index.fromOneBased(parseInt(trimmedIndexOrName));
        }

        // Need to check if index is zero if not it will parse "0" as Name
        if ("0".equals(trimmedIndexOrName)) {
            throw new ParseException(MESSAGE_NOT_INDEX_OR_NAME);
        }

        if (Name.isValidName(trimmedIndexOrName)) {
            return new Name(trimmedIndexOrName);
        }

        throw new ParseException(MESSAGE_NOT_INDEX_OR_NAME);
    }
}
