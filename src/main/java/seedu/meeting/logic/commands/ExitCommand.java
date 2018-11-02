package seedu.meeting.logic.commands;

import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.events.ui.ExitAppRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting MeetingBook as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
