package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collections;

import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.RepeatEventGenerator;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.exceptions.EventOverflowException;

/**
 * Adds an event to the scheduler.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS_ONE = "ad";
    public static final String COMMAND_ALIAS_TWO = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the scheduler. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + "[" + PREFIX_START_DATE_TIME + "DATETIME in natural language] "
            + "[" + PREFIX_END_DATE_TIME + "DATETIME in natural language] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]"
            + "[" + PREFIX_VENUE + "VENUE]"
            + "[" + PREFIX_REPEAT_TYPE + "REPEAT TYPE]"
            + "[" + PREFIX_REPEAT_UNTIL_DATE_TIME + "REPEAT UNTIL DATETIME]"
            + "[" + PREFIX_EVENT_REMINDER_DURATION + "REMINDER DURATION]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Discussion with John "
            + PREFIX_START_DATE_TIME + "today 5pm "
            + PREFIX_END_DATE_TIME + "tomorrow 3am "
            + PREFIX_DESCRIPTION + "Studying time "
            + PREFIX_VENUE + "NUS "
            + PREFIX_REPEAT_TYPE + "Daily "
            + PREFIX_REPEAT_UNTIL_DATE_TIME + "next friday 3am "
            + PREFIX_EVENT_REMINDER_DURATION + "1h "
            + PREFIX_TAG + "study "
            + PREFIX_TAG + "ad-hoc";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the scheduler";
    public static final String MESSAGE_OVERFLOW_EVENT = "This event repeats too many times";
    public static final String MESSAGE_INTERNET_ERROR = "Warning: Internet Error."
            + "Only local changes,"
            + "no effects on your Google Calender.";

    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();

    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public AddCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        try {
            model.addEvents(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(toAdd));
        } catch (EventOverflowException e) {
            throw new CommandException(MESSAGE_OVERFLOW_EVENT);
        }
        model.commitScheduler();

        boolean operationOnGoogleCalIsSuccessful =
                connectToGoogleCalendar.pushToGoogleCal(Collections.singletonList(toAdd));

        if (operationOnGoogleCalIsSuccessful | connectToGoogleCalendar.isGoogleCalendarDisabled()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getEventName()));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getEventName())
                    + "\n" + MESSAGE_INTERNET_ERROR);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.getEventName().equals(((AddCommand) other).toAdd.getEventName())
                && toAdd.getStartDateTime().equals(((AddCommand) other).toAdd.getStartDateTime())
                && toAdd.getEndDateTime().equals(((AddCommand) other).toAdd.getEndDateTime())
                && toAdd.getDescription().equals(((AddCommand) other).toAdd.getDescription())
                && toAdd.getVenue().equals(((AddCommand) other).toAdd.getVenue())
                && toAdd.getRepeatType().equals(((AddCommand) other).toAdd.getRepeatType())
                && toAdd.getRepeatUntilDateTime().equals(((AddCommand) other).toAdd.getRepeatUntilDateTime())
                && toAdd.getTags().equals(((AddCommand) other).toAdd.getTags()));
    }
}
