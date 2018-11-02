package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;

/**
 * Finds and lists all meetings in address book whose title contains any of the argument keywords.
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
