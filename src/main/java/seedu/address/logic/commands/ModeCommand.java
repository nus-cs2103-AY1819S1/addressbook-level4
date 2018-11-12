package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeModeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 *
 */
public class ModeCommand extends Command {
    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : toggles current day and night mode\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Toggled mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ChangeModeEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
