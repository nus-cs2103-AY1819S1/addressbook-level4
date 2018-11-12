package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.attendance.exceptions.DuplicateSessionException;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

// @@author triger15
/**
 * Command that creates an attendance session.
 */
public class CreateAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "create-attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an attendance.\n"
        + " Parameters: "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
        + PREFIX_SESSION_NAME + "SESSION-NAME \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "1 "
        + PREFIX_SESSION_NAME + "W4 Tutorial ";

    public static final String MESSAGE_SUCCESS = "New attendance created: %1$s";
    public static final String MESSAGE_INVALID_TUTORIAL_GROUP = "No such tutorial group.";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE =
            "This attendance session already exists in the tutorial group.";

    private final String tgId;
    private final Session session;

    public CreateAttendanceCommand(String tutorialGroupId, Session session) {
        requireAllNonNull(tutorialGroupId, session);
        this.tgId = tutorialGroupId;
        this.session = session;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.createAttendance(tgId, session);
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_TUTORIAL_GROUP);
        } catch (DuplicateSessionException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTENDANCE);
        }
        model.commitSuperTaClient();
        return new CommandResult(String.format(MESSAGE_SUCCESS, session.getSessionName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateAttendanceCommand)) {
            return false;
        }

        // state check
        CreateAttendanceCommand e = (CreateAttendanceCommand) other;
        return tgId.equals(e.tgId)
                && session.equals(e.session);
    }
}
