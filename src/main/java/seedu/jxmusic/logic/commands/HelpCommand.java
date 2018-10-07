package seedu.jxmusic.logic.commands;

import seedu.jxmusic.commons.core.EventsCenter;
import seedu.jxmusic.commons.events.ui.ShowHelpRequestEvent;
import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
