package seedu.superta.logic.commands;

// @@author Caephler

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import java.util.Optional;

import seedu.superta.commons.core.EventsCenter;
import seedu.superta.commons.events.ui.ViewSessionEvent;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Comamnd that views a session's information.
 */
public class ViewSessionCommand extends Command {
    public static final String COMMAND_WORD = "view-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views information about a specific attendance session.\n"
            + "Parameters: "
            + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
            + PREFIX_SESSION_NAME + "SESSION-NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
            + PREFIX_SESSION_NAME + "W1Tutorial";

    public static final String MESSAGE_SUCCESS = "Displayed the session: %1$s";
    public static final String MESSAGE_NO_SUCH_TUTORIAL_GROUP = "There is no such tutorial group with this ID.";
    public static final String MESSAGE_NO_SUCH_SESSION = "There is no such session in the tutorial group.";

    private final String tutorialGroupId;
    private final String sessionName;

    public ViewSessionCommand(String tutorialGroupId, String sessionName) {
        requireAllNonNull(tutorialGroupId, sessionName);
        this.tutorialGroupId = tutorialGroupId;
        this.sessionName = sessionName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasTutorialGroup(tutorialGroupId)) {
            throw new CommandException(MESSAGE_NO_SUCH_TUTORIAL_GROUP);
        }
        TutorialGroup tutorialGroup = model.getTutorialGroup(tutorialGroupId).get();
        Optional<Session> sessionOptional = tutorialGroup.getSessionByName(sessionName);
        if (!sessionOptional.isPresent()) {
            throw new CommandException(MESSAGE_NO_SUCH_SESSION);
        }
        Session session = sessionOptional.get();
        EventsCenter.getInstance().post(new ViewSessionEvent(session, tutorialGroup));
        return new CommandResult(String.format(MESSAGE_SUCCESS, session));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewSessionCommand)) {
            return false;
        }
        ViewSessionCommand otherCommand = (ViewSessionCommand) other;
        return tutorialGroupId.equals(otherCommand.tutorialGroupId) && sessionName.equals(otherCommand.sessionName);
    }
}
