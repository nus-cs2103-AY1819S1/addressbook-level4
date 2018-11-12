package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.exceptions.DuplicateStudentException;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Command that adds student to a tutorial group.
 */
public class AddStudentToTutorialGroupCommand extends Command {
    public static final String COMMAND_WORD = "add-to-tutorial-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to a tutorial group.\n"
        + "Parameters: "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
        + PREFIX_GENERAL_STUDENT_ID + "STUDENT-ID\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
        + PREFIX_GENERAL_STUDENT_ID + "A0166733Y";

    public static final String MESSAGE_SUCCESS = "Added a student to tutorial group.";

    private final String tgId;
    private final StudentId studentId;

    public AddStudentToTutorialGroupCommand(String tutorialGroupId, StudentId studentId) {
        requireAllNonNull(tutorialGroupId, studentId);
        tgId = tutorialGroupId;
        this.studentId = studentId;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.addStudentToTutorialGroup(tgId, studentId);
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException("No such tutorial group.");
        } catch (StudentNotFoundException e) {
            throw new CommandException("No such student.");
        } catch (DuplicateStudentException e) {
            throw new CommandException("Student is already in this tutorial group!");
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
        if (!(other instanceof AddStudentToTutorialGroupCommand)) {
            return false;
        }

        //state check
        AddStudentToTutorialGroupCommand e = (AddStudentToTutorialGroupCommand) other;
        return tgId.equals(e.tgId)
                && studentId.equals(e.studentId);
    }
}
