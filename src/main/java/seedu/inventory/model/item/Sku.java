package seedu.inventory.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's SKU in the inventory book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSku(String)}
 */
public class Sku {

    private static final String SPECIAL_CHARACTERS = "_-";
    public static final String MESSAGE_SKU_CONSTRAINTS = "SKUs should only contain alphanumeric characters,  "
            + "spaces, or these special characters: " + SPECIAL_CHARACTERS + " and it should not be blank. "
            + "It should also be unique. ";
    // alphanumeric and special characters
    private static final String SKU_VALIDATION_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";

    public final String value;

    /**
     * Constructs an {@code Sku}.
     *
     * @param sku A valid SKU.
     */
    public Sku(String sku) {
        requireNonNull(sku);
        checkArgument(isValidSku(sku), MESSAGE_SKU_CONSTRAINTS);
        value = sku;
    }

    /**
     * Returns if a given string is a valid SKU.
     */
    public static boolean isValidSku(String test) {
        return test.matches(SKU_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Sku // instanceof handles nulls
                && value.equals(((Sku) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
