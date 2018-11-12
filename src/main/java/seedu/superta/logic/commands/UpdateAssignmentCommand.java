package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NEW_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import java.util.Optional;

import seedu.superta.commons.util.CollectionUtil;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.GradeBook;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentNameException;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Update existing assignment details in the SuperTA client.
 */
public class UpdateAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "update-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Updates an existing assignment details (ie. assignment title, maximum marks).\n"
            + "Parameters: " + COMMAND_WORD + " "
            + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
            + PREFIX_GENERAL_ASSIGNMENT_TITLE + "OLD-ASSIGNMENT-TITLE \n"
            + "[" + PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE + "NEW-ASSIGNMENT-TITLE] "
            + "[" + PREFIX_ASSIGNMENT_NEW_MAX_MARKS + "NEW-ASSIGNMENT-MAX-MARKS] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
            + PREFIX_GENERAL_ASSIGNMENT_TITLE + "Lab 1 "
            + PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE + "Lab 2 "
            + PREFIX_ASSIGNMENT_NEW_MAX_MARKS + "50.0";

    public static final String MESSAGE_SUCCESS = "Tutorial %1$s - Assignment %2$s details has been updated! ";
    public static final String MESSAGE_FAILURE_NO_TUTORIAL_GROUP = "Tutorial group does not exist.";
    public static final String MESSAGE_FAILURE_NO_ASSIGNMENT = "Assignment does not exist.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT_NAME = "Assignment name already exists in the database.";
    public static final Double INVALID_VALUE = -1.0;

    private final String tutorialGroupId;
    private final Title assignmentToChange;
    private final UpdateAssignmentDescriptor updateAssignmentDescriptor;

    public UpdateAssignmentCommand(String tutorialGroupId, Title assignmentToChange,
                                   UpdateAssignmentDescriptor updateAssignmentDescriptor) {
        requireNonNull(tutorialGroupId);
        requireNonNull(assignmentToChange);
        requireNonNull(updateAssignmentDescriptor);

        this.tutorialGroupId = tutorialGroupId;
        this.assignmentToChange = assignmentToChange;
        this.updateAssignmentDescriptor = updateAssignmentDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Assignment assignmentChanged = createChangedAssignment(assignmentToChange, updateAssignmentDescriptor);

        try {
            model.updateAssignment(tutorialGroupId, assignmentToChange, assignmentChanged);
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE_NO_TUTORIAL_GROUP);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE_NO_ASSIGNMENT);
        } catch (DuplicateAssignmentNameException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT_NAME);
        }

        model.commitSuperTaClient();
        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialGroupId,
                assignmentToChange));
    }

    /**
     * Creates and returns an {@code assignmentChanged} with the details of {@code assignmentToChange}
     * edited with {@code updateAssignmentDescriptor}.
     */
    private static Assignment createChangedAssignment(Title assignmentToChange,
                                                     UpdateAssignmentDescriptor updateAssignmentDescriptor) {
        assert assignmentToChange != null;

        Title updatedAssignmentTitle = updateAssignmentDescriptor.getAssignmentTitle()
                .orElse(assignmentToChange);
        Double updatedMaxMarks = updateAssignmentDescriptor.getMaxMarks().orElse(INVALID_VALUE);

        return new Assignment(updatedAssignmentTitle, updatedMaxMarks);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateAssignmentCommand)) {
            return false;
        }

        // state check
        UpdateAssignmentCommand e = (UpdateAssignmentCommand) other;
        return tutorialGroupId.equals(e.tutorialGroupId)
                && assignmentToChange.equals(e.assignmentToChange)
                && updateAssignmentDescriptor.equals(e.updateAssignmentDescriptor);
    }

    /**
     * Stores the details to update the assignment with. Each non-empty field value will replace the
     * corresponding field value of the assignment.
     */
    public static class UpdateAssignmentDescriptor implements Descriptor {
        private Title assignmentTitle;
        private Double maxMarks;
        private GradeBook gradeBook;

        public UpdateAssignmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public UpdateAssignmentDescriptor(UpdateAssignmentDescriptor toCopy) {
            setAssignmentTitle(toCopy.assignmentTitle);
            setMaxMarks(toCopy.maxMarks);
            setGradeBook(toCopy.gradeBook);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(assignmentTitle, maxMarks);
        }

        public void setAssignmentTitle(Title assignmentTitle) {
            this.assignmentTitle = assignmentTitle;
        }

        public Optional<Title> getAssignmentTitle() {
            return Optional.ofNullable(assignmentTitle);
        }

        public void setMaxMarks(Double maxMarks) {
            this.maxMarks = maxMarks;
        }

        public Optional<Double> getMaxMarks() {
            return Optional.ofNullable(maxMarks);
        }

        public void setGradeBook(GradeBook gradeBook) {
            this.gradeBook = gradeBook;
        }

        public Optional<GradeBook> getGradeBook() {
            return Optional.ofNullable(gradeBook);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateAssignmentDescriptor)) {
                return false;
            }

            // state check
            UpdateAssignmentDescriptor e = (UpdateAssignmentDescriptor) other;

            return getAssignmentTitle().equals(e.getAssignmentTitle())
                    && getMaxMarks().equals(e.getMaxMarks());
        }
    }
}
