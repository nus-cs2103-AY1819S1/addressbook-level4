package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.meeting.commons.core.Messages;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.meeting.Meeting;

/**
 * Finds and lists all meetings in MeetingBook whose title contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMeetingCommand extends FindCommand<Meeting> {

    public static final String FIND_MEETING_PARAM = "meeting";
    public static final String FIND_MEETING_PARAM_SHORT = "m";

    public FindMeetingCommand(Predicate<Meeting> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMeetingList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_MEETINGS_FOUND_OVERVIEW, model.getFilteredMeetingList().size()));
    }
}
