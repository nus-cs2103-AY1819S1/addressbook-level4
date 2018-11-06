package seedu.restaurant.logic.parser.sales;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;

/**
 * Contains utility methods used for parsing strings sales-related classes.
 */
public class SalesParserUtil {

    // This class should not be instantiated.
    private SalesParserUtil() {
        throw new AssertionError("SalesParserUtil should not be instantiated.");
    }

    /**
     * Parses a {@code String date} into a {@code Date}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String name} into a {@code ItemName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ItemName parseItemName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ItemName.isValidName(trimmedName)) {
            throw new ParseException(ItemName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new ItemName(trimmedName);
    }

    /**
     * Parses a {@code String quantitySold} into a {@code QuantitySold}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code quantitySold} is invalid.
     */
    public static QuantitySold parseQuantitySold(String quantitySold) throws ParseException {
        requireNonNull(quantitySold);
        String trimmedQuantitySold = quantitySold.trim();
        if (!QuantitySold.isValidQuantity(trimmedQuantitySold)) {
            throw new ParseException(QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        return new QuantitySold(trimmedQuantitySold);
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
}
