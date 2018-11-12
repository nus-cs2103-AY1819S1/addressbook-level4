package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.assignment.exceptions.GradeException;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Command that grades assignments.
 */
public class GradeAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Grades an assignment.\n"
        + "Parameters: "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
        + PREFIX_GENERAL_ASSIGNMENT_TITLE + "ASSIGNMENT-TITLE "
        + PREFIX_GENERAL_STUDENT_ID + "STUDENT-ID "
        + PREFIX_ASSIGNMENT_MARKS + "MARKS\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
        + PREFIX_GENERAL_ASSIGNMENT_TITLE + "lab1 "
        + PREFIX_GENERAL_STUDENT_ID + "A0166733Y"
        + PREFIX_ASSIGNMENT_MARKS + "40";

    public static final String MESSAGE_SUCCESS = "Student graded!";

    private final Grade grade;

    public GradeAssignmentCommand(Grade grade) {
        requireNonNull(grade);
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.grade(grade);
        } catch (StudentNotFoundException e) {
            throw new CommandException("Student is not in the specified tutorial group!");
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException("No such tutorial group.");
        } catch (AssignmentNotFoundException e) {
            throw new CommandException("No such assignment.");
        } catch (GradeException e) {
            throw new CommandException("Grade should not be above maximum marks of assignment.");
        }

        model.commitSuperTaClient();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeAssignmentCommand)) {
            return false;
        }

        //state check
        GradeAssignmentCommand e = (GradeAssignmentCommand) other;
        return grade.equals(e.grade);
    }
}
