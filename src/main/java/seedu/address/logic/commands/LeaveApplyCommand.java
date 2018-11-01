package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.User;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;

/**
 * Apply for leave.
 */
public class LeaveApplyCommand extends Command {

    public static final String COMMAND_WORD = "leaveapply";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Apply for leave. "
            + "Parameters: "
            + PREFIX_LEAVE_DESCRIPTION + " DESCRIPTION "
            + "[" + PREFIX_LEAVE_DATE + " YYYY-MM-DD]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LEAVE_DESCRIPTION + " John's family holiday "
            + PREFIX_LEAVE_DATE + " 2018-12-10 "
            + PREFIX_LEAVE_DATE + " 2018-12-11 "
            + PREFIX_LEAVE_DATE + " 2018-12-12 ";

    public static final String MESSAGE_SUCCESS = "New leave application created: %1$s";
    public static final String MESSAGE_ADMIN_FAILURE = "Admin user cannot apply for leave";

    private final LeaveApplication leaveApplicationToAdd;

    /**
     * Creates an LeaveApplyCommand to add the specified {@code LeaveApplication}
     */
    public LeaveApplyCommand(LeaveApplication leaveApplication) {
        requireAllNonNull(leaveApplication);
        leaveApplicationToAdd = leaveApplication;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getLoggedInUser().equals(User.getAdminUser())) {
            throw new CommandException(MESSAGE_ADMIN_FAILURE);
        }

        Person personToAddLeave = model.getLoggedInUser().getPerson();
        Person updatedPerson = createPersonWithNewLeaveApplication(personToAddLeave, leaveApplicationToAdd);
        model.updatePerson(personToAddLeave, updatedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, leaveApplicationToAdd));
    }

    /**
     * Creates and returns a {@code Person} with a new leave application added to it.
     */
    private static Person createPersonWithNewLeaveApplication(Person person, LeaveApplication leaveApplication) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Salary salary = person.getSalary();
        Username username = person.getUsername();
        Password password = person.getPassword();
        Set<Project> projects = person.getProjects();
        PermissionSet pSet = person.getPermissionSet();
        List<LeaveApplication> leaveApplications = new ArrayList<>(person.getLeaveApplications());
        leaveApplications.add(leaveApplication);
        return new Person(name, phone, email, address, salary, username, password, projects, pSet, leaveApplications);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveApplyCommand // instanceof handles nulls
                && leaveApplicationToAdd.equals(((LeaveApplyCommand) other).leaveApplicationToAdd));
    }
}
