package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.meeting.SameMeetingDayPredicate;

//@@author AyushChatto
/**
 * Returns details of meetings scheduled at a particular time.
 */
public class MeetingsCommand extends Command {
    public static final String COMMAND_WORD = "meetings";
    public static final String COMMAND_ALIAS = "m";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all persons who have meetings "
            + "scheduled on the same day, if it is provided. Lists all meetings scheduled otherwise. \n"
            + "Parameters: [DD/MM/YY] \n"
            + "Example: " + COMMAND_WORD + " 23/02/18";

    private final SameMeetingDayPredicate predicate;

    public MeetingsCommand(SameMeetingDayPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEETINGS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingsCommand // instanceof handles nulls
                && predicate.equals(((MeetingsCommand) other).predicate)); // state check
    }
}
