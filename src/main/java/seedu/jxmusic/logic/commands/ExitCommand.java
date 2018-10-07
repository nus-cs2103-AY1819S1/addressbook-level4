package seedu.jxmusic.logic.commands;

import seedu.jxmusic.commons.core.EventsCenter;
import seedu.jxmusic.commons.events.ui.ExitAppRequestEvent;
import seedu.jxmusic.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
