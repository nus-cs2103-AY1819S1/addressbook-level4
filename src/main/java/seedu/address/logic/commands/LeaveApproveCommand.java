package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.model.permission.Permission;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;

/**
 * Approves an existing leave application.
 */
public class LeaveApproveCommand extends Command {

    public static final String COMMAND_WORD = "leaveapprove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Approve the leave of the leave application identified "
            + "by the index number used in the displayed leave application list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_LEAVE_APPROVE_SUCCESS = "Leave approved: %1$s";

    private final Index index;

    /**
     * @param index of the leave application in the filtered leave application list to edit
     */
    public LeaveApproveCommand(Index index) {
        requireNonNull(index);

        requiredPermission.addPermissions(Permission.APPROVE_LEAVE);
        this.index = index;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<LeaveApplicationWithEmployee> lastShownList = model.getFilteredLeaveApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        }

        LeaveApplicationWithEmployee leaveApplicationToApprove = lastShownList.get(index.getZeroBased());
        Person personToApproveLeave = leaveApplicationToApprove.getEmployee();
        LeaveApplication approvedLeaveApplication = createApprovedLeaveApplication(leaveApplicationToApprove);
        Person updatedPerson = createPersonWithApprovedLeaveApplication(personToApproveLeave,
                new LeaveApplication(leaveApplicationToApprove), approvedLeaveApplication);
        model.updatePerson(personToApproveLeave, updatedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_LEAVE_APPROVE_SUCCESS, approvedLeaveApplication));
    }

    /**
     * Takes and existing {@code LeaveApplication} and returns an identical one with a its {@code LeaveStatus} approved.
     */
    private static LeaveApplication createApprovedLeaveApplication(LeaveApplication leaveApplication) {
        assert leaveApplication != null;
        LeaveApplication approvedLeaveApplication = new LeaveApplication(leaveApplication.getDescription(),
                new LeaveStatus("APPROVED"), leaveApplication.getDates());
        return approvedLeaveApplication;
    }

    /**
     * Creates and returns a {@code Person} with a its {@code leaveApplication} approved.
     */
    private static Person createPersonWithApprovedLeaveApplication(Person person, LeaveApplication leaveApplication,
                                                                   LeaveApplication approvedLeaveApplication) {
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
        leaveApplications.set(leaveApplications.indexOf(leaveApplication), approvedLeaveApplication);
        return new Person(name, phone, email, address, salary, username, password, projects, pSet, leaveApplications);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LeaveApproveCommand)) {
            return false;
        }

        // state check
        LeaveApproveCommand e = (LeaveApproveCommand) other;
        return index.equals(e.index);
    }

}
