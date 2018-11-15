package seedu.address.model.services;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the price of a Service provided by the clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidServicePrice(String)}
 */
public class ServicePrice {
    public static final String MESSAGE_SERVICE_PRICE_CONSTRAINTS =
            "Price of a service should be an integer.";
    public static final String SERVICE_PRICE_VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    public final String price;

    /**
     * Constructs a {@code ServicePrice}.
     *
     * @param number A positive integer.
     */
    public ServicePrice(String number) {
        requireNonNull(number);
        checkArgument(isValidServicePrice(number), MESSAGE_SERVICE_PRICE_CONSTRAINTS);
        this.price = number;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidServicePrice(String test) {
        return test.matches(SERVICE_PRICE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ServicePrice // instanceof handles nulls
                && price.equals(((ServicePrice) other).price)); // state check
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
