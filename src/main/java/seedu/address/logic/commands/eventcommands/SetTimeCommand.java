//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Sets the time of an event.
 */
public class SetTimeCommand extends Command {

    public static final String COMMAND_WORD = "setTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the time for the pre-selected event.";
    public static final String MESSAGE_SUCCESS = "Time from %1$s to $2$s set for %3$s";

    private final LocalTime startTime;
    private final LocalTime endTime;
    private Event event;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public SetTimeCommand(LocalTime startTime, LocalTime endTime) {
        requireNonNull(startTime);
        requireNonNull(endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        event = history.getSelectedEvent();
        if (event == null) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        }

        try {
            Person person = history.getSelectedPerson();
            if (!person.equals(event.getOrganiser())) {
                throw new CommandException(Messages.MESSAGE_NOT_EVENT_ORGANISER);
            }
        } catch (NoUserLoggedInException e) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        }

        List<Event> lastShownList = model.getFilteredEventList();
        int index = lastShownList.indexOf(event);
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        model.updateEvent(index, event);
        model.commitAddressBook();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        return new CommandResult(String.format(MESSAGE_SUCCESS, startTime.format(timeFormat),
                endTime.format(timeFormat), event));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetTimeCommand // instanceof handles nulls
                && startTime.equals(((SetTimeCommand) other).startTime)
                && endTime.equals(((SetTimeCommand) other).endTime)); // state check
    }
}
