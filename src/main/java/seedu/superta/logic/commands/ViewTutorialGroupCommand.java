package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;

import seedu.superta.commons.core.EventsCenter;
import seedu.superta.commons.events.ui.TutorialGroupSelectedEvent;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Command that views a tutorial group's information.
 */
public class ViewTutorialGroupCommand extends Command {
    public static final String COMMAND_WORD = "view-tutorial-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views information about a specific tutorial group.\n"
        + "Parameters: "
        + PREFIX_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TUTORIAL_GROUP_ID + "04a";

    public static final String MESSAGE_SUCCESS = "Displayed the tutorial group: %1$s";
    public static final String MESSAGE_NO_SUCH_TUTORIAL_GROUP = "There is no such tutorial group with this ID.";

    private final String tutorialGroupId;

    public ViewTutorialGroupCommand(String id) {
        tutorialGroupId = id;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasTutorialGroup(tutorialGroupId)) {
            throw new CommandException(MESSAGE_NO_SUCH_TUTORIAL_GROUP);
        }
        TutorialGroup tutorialGroup = model.getTutorialGroup(tutorialGroupId).get();
        EventsCenter.getInstance().post(new TutorialGroupSelectedEvent(tutorialGroup));
        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialGroup));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewTutorialGroupCommand)) {
            return false;
        }

        //state check
        ViewTutorialGroupCommand e = (ViewTutorialGroupCommand) other;
        return tutorialGroupId.equals(e.tutorialGroupId);
    }
}
