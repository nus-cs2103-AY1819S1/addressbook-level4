package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;

/**
 * Edits the details of an existing assignment in the deadline book.
 */
public class EditAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "editAssignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the assignment identified "
            + "by the index number used in the displayed assignment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_DATE + "DEADLINE] "
            + "[" + PREFIX_MARK + "MAX_MARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WEIGHT + "35 "
            + PREFIX_MARK + "25";

    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the deadline book.";

    protected final Index index;
    protected final EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param index of the assignment in the filtered assignment list to edit
     * @param editAssignmentDescriptor details to edit the assignment with
     */
    public EditAssignmentCommand(Index index, EditAssignmentDescriptor editAssignmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAssignmentDescriptor);

        this.index = index;
        this.editAssignmentDescriptor = new EditAssignmentDescriptor(editAssignmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToEdit = lastShownList.get(index.getZeroBased());
        Assignment editedAssignment = createEditedAssignment(assignmentToEdit, editAssignmentDescriptor);

        if (!assignmentToEdit.isSameAssignment(editedAssignment) && model.hasAssignment(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.updateAssignment(assignmentToEdit, editedAssignment);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment));
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code assignmentToEdit}
     * edited with {@code editAssignmentDescriptor}.
     */
    protected Assignment createEditedAssignment(Assignment assignmentToEdit,
                                                EditAssignmentDescriptor editAssignmentDescriptor) {
        assert assignmentToEdit != null;

        AssignmentName updatedName = editAssignmentDescriptor.getName().orElse(assignmentToEdit.getName());
        Weight updatedWeight = editAssignmentDescriptor.getWeight().orElse(assignmentToEdit.getWeight());
        Deadline updatedDeadline = editAssignmentDescriptor.getDeadline().orElse(assignmentToEdit.getDeadline());
        Mark updatedMaxMark = editAssignmentDescriptor.getMaxMark().orElse(assignmentToEdit.getMaxMark());
        String id = assignmentToEdit.getUniqueId();

        return new Assignment(
                updatedName, updatedWeight, updatedDeadline, updatedMaxMark, id
        );
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
        private AssignmentName name;
        private Weight weight;
        private Deadline deadline;
        private Mark maxMark;

        public EditAssignmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAssignmentDescriptor(EditAssignmentDescriptor toCopy) {
            setName(toCopy.name);
            setWeight(toCopy.weight);
            setDeadline(toCopy.deadline);
            setMaxMark(toCopy.maxMark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, weight, deadline, maxMark);
        }

        public void setName(AssignmentName name) {
            this.name = name;
        }

        public Optional<AssignmentName> getName() {
            return Optional.ofNullable(name);
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(weight);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setMaxMark(Mark mark) {
            this.maxMark = mark;
        }

        public Optional<Mark> getMaxMark() {
            return Optional.ofNullable(maxMark);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAssignmentDescriptor)) {
                return false;
            }

            // state check
            EditAssignmentDescriptor e = (EditAssignmentDescriptor) other;

            return getName().equals(e.getName())
                    && getWeight().equals(e.getWeight())
                    && getDeadline().equals(e.getDeadline())
                    && getMaxMark().equals(e.getMaxMark());
        }
    }
}
