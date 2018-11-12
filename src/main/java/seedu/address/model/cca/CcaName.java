package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a CCA's name in the cca book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCcaName(String)}
 *
 * @author ericyjw
 */
public class CcaName {
    public static final String MESSAGE_NAME_CONSTRAINTS =
        "CCA names should only contain alphabet characters and spaces, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    private final String ccaName;

    /**
     * Constructs a {@code CcaName}.
     *
     * @param name a valid CCA name
     */
    public CcaName(String name) {
        requireNonNull(name);
        checkArgument(isValidCcaName(name), MESSAGE_NAME_CONSTRAINTS);

        String[] arr = name.trim().split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                .append(arr[i].substring(1)).append(" ");
        }
        ccaName = sb.toString().trim();
    }

    public String getNameOfCca() {
        return ccaName;
    }

    /**
     * Returns true if a given string is a valid CCA name.
     */
    public static boolean isValidCcaName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.ccaName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CcaName // instanceof handles nulls
            && ccaName.equals(((CcaName) other).ccaName)); // state check
    }

    @Override
    public int hashCode() {
        return ccaName.hashCode();
    }
}
