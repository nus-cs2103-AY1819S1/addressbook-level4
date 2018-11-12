package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Salary class to store the necessary information about a salary.
 */

public class Salary {

    public static final Salary DEFAULT_INITIAL_SALARY = new Salary("0");

    public static final String MESSAGE_CONSTRAINTS =
            "Salary should only contain integers with no spaces or commas";

    public static final String SALARY_VALIDATION_REGEX = "^([0-9]+.[0-9]{1,2})|0$";

    public final String salary;

    public Salary(String value) {
        requireNonNull(value);
        checkArgument(isValidSalary(value), MESSAGE_CONSTRAINTS);
        salary = value;
    }

    public static boolean isValidSalary(String test) {
        return test.matches(SALARY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Salary // instanceof handles nulls
            && salary.equals(((Salary) other).salary)); // state check
    }

    @Override
    public String toString() {
        return String.valueOf(salary);
    }


}
