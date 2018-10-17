package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's salary in OASIS.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(String)}
 */
public class Salary {

    public static final String SALARY_CONSTRAINTS =
            "Salary should only contain numbers";
    public static final String SALARY_VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
    * Constructs a {@code Phone}.
    *
    * @param salary A valid salary number.
    */
    public Salary(String salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(salary), SALARY_CONSTRAINTS);
        value = salary;
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidSalary(String test) {
        return test.matches(SALARY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
              || (other instanceof Salary // instanceof handles nulls
              && value.equals(((Salary) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
