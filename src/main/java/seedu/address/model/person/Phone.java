package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in SSENISUB.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, starts with digit '6', '8' or '9' and it should be 8 digits "
                    + "long";
    public static final String VALIDATION_REGEX = "^([6,8-9]{1})([0-9]{7})";
    public final String value;

    private final boolean isPrivate;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
        isPrivate = false;
    }

    /**
     * Constructs a private {@code Phone}.
     * @param phone A valid phone number.
     * @param privacy states that this phone is private.
     */
    public Phone(String phone, String privacy) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
        if (privacy.equals("Y")) {
            isPrivate = true;
        } else {
            isPrivate = false;
        }
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Getter for isPrivate
     * @return isPrivate
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value))
                && isPrivate() == ((Phone) other).isPrivate(); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
