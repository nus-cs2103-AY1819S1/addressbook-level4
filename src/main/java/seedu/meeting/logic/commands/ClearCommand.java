package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.Model;

/**
 * Clears the MeetingBook.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "MeetingBook has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new MeetingBook());
        model.commitMeetingBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
