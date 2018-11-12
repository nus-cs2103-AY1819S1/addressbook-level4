package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.superta.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.exceptions.StudentNotFoundException;

// @@author triger15
/**
 * Command that adds feedback for a student.
 */
public class FeedbackCommand extends Command {
    public static final String COMMAND_WORD = "feedback";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a feedback to a student.\n"
        + "Parameters: "
        + PREFIX_STUDENT_ID + "STUDENT-ID "
        + PREFIX_FEEDBACK + "FEEDBACK\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STUDENT_ID + "A0123566T "
        + PREFIX_FEEDBACK + "Is generally attentive during class. However, needs to speak up more.";

    public static final String MESSAGE_SUCCESS = "New feedback created: %1$s";
    public static final String MESSAGE_INVALID_STUDENT = "No such student.";

    private final StudentId studentId;
    private final Feedback feedback;

    /**
     * @param studentId of the student to add feedback to
     * @param feedback of the student to create
     */
    public FeedbackCommand(StudentId studentId, Feedback feedback) {
        requireAllNonNull(studentId, feedback);
        this.studentId = studentId;
        this.feedback = feedback;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.addFeedback(feedback, studentId);
        } catch (StudentNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitSuperTaClient();
        return new CommandResult(String.format(MESSAGE_SUCCESS, feedback));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FeedbackCommand)) {
            return false;
        }

        // state check
        FeedbackCommand e = (FeedbackCommand) other;
        return studentId.equals(e.studentId)
                && feedback.equals(e.feedback);
    }
}
