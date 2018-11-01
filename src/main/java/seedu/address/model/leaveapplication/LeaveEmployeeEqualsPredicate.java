package seedu.address.model.leaveapplication;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code LeaveApplication}'s {@code Person} matches the {@code Person} given
 */
public class LeaveEmployeeEqualsPredicate implements Predicate<LeaveApplicationWithEmployee> {
    private final Person employee;

    public LeaveEmployeeEqualsPredicate(Person employee) {
        this.employee = employee;
    }

    @Override
    public boolean test(LeaveApplicationWithEmployee leaveApplication) {
        return leaveApplication.getEmployee().isSamePerson(employee);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveEmployeeEqualsPredicate // instanceof handles nulls
                && employee.equals(((LeaveEmployeeEqualsPredicate) other).employee)); // state check
    }

}
