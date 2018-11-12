package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.List;
import java.util.ListIterator;

import seedu.superta.commons.core.EventsCenter;
import seedu.superta.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.exceptions.StudentNotFoundException;

/**
 * Lists the previous feedback given to the student identified.
 */
public class ViewStudentFeedbackCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_SUCCESS = "Student ID: %s\nFeedback:\n";
    public static final String MESSAGE_FAILURE = "Student not found!\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the feedback given to the student"
            + " indicated by Student ID and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_STUDENT_ID + "STUDENT-ID " + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_STUDENT_ID + "A0123456Z";

    private final StudentId studentId;

    public ViewStudentFeedbackCommand(StudentId studentId) {
        requireNonNull(studentId);
        this.studentId = studentId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        String studentInfo = String.format(MESSAGE_SUCCESS, studentId);
        StringBuilder allFeedback = new StringBuilder(studentInfo);
        try {
            List<Feedback> feedbackList = model.viewFeedback(studentId);
            ListIterator<Feedback> iterator = feedbackList.listIterator();
            while (iterator.hasNext()) {
                String nextFeedback = iterator.next().toString().trim();
                if (nextFeedback.length() > 0) {
                    allFeedback.append(iterator.previousIndex() + " ");
                    allFeedback.append(nextFeedback + "\n");
                }
            }
        } catch (StudentNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        Student student = model.getSuperTaClient().getStudentList().stream()
                .filter(stud -> stud.getStudentId().equals(studentId))
                .findFirst().get();
        EventsCenter.getInstance().post(new StudentPanelSelectionChangedEvent(student));
        return new CommandResult(allFeedback.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewStudentFeedbackCommand) // instanceof handles nulls
                && studentId.equals(((ViewStudentFeedbackCommand) other).studentId); // state check
    }

}
