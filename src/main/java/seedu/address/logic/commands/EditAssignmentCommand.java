package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 * Edits the details of an existing assignment in the address book.
 */
public class EditAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "editassignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the assignment identified "
            + "by the index number used in the displayed assignment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ASSIGNMENT_NAME + " ASSIGNMENT NAME] "
            + "[" + PREFIX_AUTHOR + " AUTHOR] "
            + "[" + PREFIX_ASSIGNMENT_DESCRIPTION + " DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT_NAME + " OASIS v2 "
            + PREFIX_AUTHOR + " MARY GOSLOW";

    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the assignment list.";

    private final Index index;
    private final EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param index of the assignment in the filtered person list to edit
     * @param editAssignmentDescriptor details to edit the person with
     */
    public EditAssignmentCommand(Index index, EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAssignmentDescriptor);

        requiredPermission.addPermissions(Permission.EDIT_EMPLOYEE);
        this.index = index;
        this.editAssignmentDescriptor = new EditAssignmentCommand.EditAssignmentDescriptor(editAssignmentDescriptor);
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToEdit = lastShownList.get(index.getZeroBased());

        /*List<Person> personList = model.getFilteredPersonList();
        Set<Project> assignmentNames;
        for(Person p : personList){
            //System.out.println(p.getProjects());
            assignmentNames = p.getProjects();
            for (Project pro : assignmentNames) {
                //System.out.println(p.getName() + " " + pro.getProjectName());
                //System.out.println(pro.getProjectName().toString() + " " + assignmentToEdit.getProjectName());
                if(pro.getProjectName().toString().equals(assignmentToEdit.getProjectName())) {
                    System.out.println("Need to edit.");
                }
            }
        }*/

        Assignment editedAssignment = createEditedAssignment(assignmentToEdit, editAssignmentDescriptor);

        if (!assignmentToEdit.isSameAssignment(editedAssignment) && model.hasAssignment(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.updateAssignment(assignmentToEdit, editedAssignment);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment));
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code assignmentToEdit}
     * edited with {@code editAssignmentDescriptor}.
     */
    public static Assignment createEditedAssignment(Assignment assignmentToEdit,
                                                    EditAssignmentCommand.EditAssignmentDescriptor
                                                            editAssignmentDescriptor) {
        assert assignmentToEdit != null;

        ProjectName updatedAssignmentName =
                editAssignmentDescriptor.getAssignmentName().orElse(assignmentToEdit.getProjectName());
        Name updatedAuthor = editAssignmentDescriptor.getAuthor().orElse(assignmentToEdit.getAuthor());
        Description updatedDescription =
                editAssignmentDescriptor.getDescription().orElse(assignmentToEdit.getDescription());

        return new Assignment(updatedAssignmentName, updatedAuthor, updatedDescription);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public static Person createEditedPerson(Person personToEdit, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Salary updatedSalary = editPersonDescriptor.getSalary().orElse(personToEdit.getSalary());
        Set<Project> updatedProjects = editPersonDescriptor.getProjects().orElse(personToEdit.getProjects());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSalary,
                personToEdit.getUsername(), personToEdit.getPassword(), updatedProjects, personToEdit.getPermissionSet(),
                personToEdit.getLeaveApplications());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAssignmentCommand)) {
            return false;
        }

        // state check
        EditAssignmentCommand e = (EditAssignmentCommand) other;
        return index.equals(e.index)
                && editAssignmentDescriptor.equals(e.editAssignmentDescriptor);
    }

    /**
     * Stores the details to edit the assignment with. Each non-empty field value will replace the
     * corresponding field value of the assignment.
     */
    public static class EditAssignmentDescriptor {
        private ProjectName assignmentName;
        private Name author;
        private Description description;

        public EditAssignmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAssignmentDescriptor(EditAssignmentCommand.EditAssignmentDescriptor toCopy) {
            setAssignmentName(toCopy.assignmentName);
            setAuthor(toCopy.author);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(assignmentName, author, description);
        }

        public void setAssignmentName(ProjectName name) {
            this.assignmentName = name;
        }

        public Optional<ProjectName> getAssignmentName() {
            return Optional.ofNullable(assignmentName);
        }

        public void setAuthor(Name author) {
            this.author = author;
        }

        public Optional<Name> getAuthor() {
            return Optional.ofNullable(author);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAssignmentCommand.EditAssignmentDescriptor)) {
                return false;
            }

            // state check
            EditAssignmentCommand.EditAssignmentDescriptor e = (EditAssignmentCommand.EditAssignmentDescriptor) other;

            return getAssignmentName().equals(e.getAssignmentName())
                    && getAuthor().equals(e.getAuthor())
                    && getDescription().equals(e.getDescription());
        }
    }
}
