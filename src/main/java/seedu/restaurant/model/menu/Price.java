package seedu.restaurant.model.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

//@@author yican95
/**
 * Represents an Item's price in the menu.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should only contain numbers, and it should be at most 2 decimal place";
    public static final String MESSAGE_PERCENT_CONSTRAINTS =
            "Percent should only contain numbers, and it should be at most 2 digits";
    private static final String DECIMAL_PLACE_REGEX = "\\d{0,2}";
    private static final String PERCENT_VALIDATION_REGEX = "\\d{0,2}";
    private static final String PRICE_VALIDATION_REGEX = "\\d+" + ".?" + DECIMAL_PLACE_REGEX;
    private static final double MAX_PERCENT = 100.0;
    private final double originalValue;
    private double value;
    private double percent;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        originalValue = Double.parseDouble(price);
        value = Double.parseDouble(price);
        percent = 0;
    }

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price, String originalPrice, String percent) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        originalValue = Double.parseDouble(originalPrice);
        value = Double.parseDouble(price);
        this.percent = Double.parseDouble(percent);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid percent.
     */
    public static boolean isValidPercent(String test) {
        return test.matches(PERCENT_VALIDATION_REGEX);
    }

    public double getValue() {
        return value;
    }

    public double getOriginalValue() {
        return originalValue;
    }

    public double getPercent() {
        return percent;
    }

    public void setValue(double percent) {
        this.percent = percent;
        value = originalValue * ((MAX_PERCENT - percent) / MAX_PERCENT);
    }

    @Override
    public String toString() {
        //return valueInString;
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                    && value == ((Price) other).value // state check
                    && originalValue == ((Price) other).originalValue);
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
