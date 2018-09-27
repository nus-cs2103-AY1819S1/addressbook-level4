package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
 * Sets the date of an event.
 */
public class SetDateCommand extends Command {

    public static final String COMMAND_WORD = "setDate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the date for the pre-selected event.";
    public static final String MESSAGE_SUCCESS = "Date %1$s set for %2$s";

    private final LocalDate date;
    private Event event;

    /**
     * Creates an SetDateCommand to add a date to the specified {@code Event}
     */
    public SetDateCommand(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    public Event getEvent() {
        return event;
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

        event.setDate(date);
        model.commitAddressBook();
        model.updateEvent(event, event);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new CommandResult(String.format(MESSAGE_SUCCESS, date.format(dateFormat), event));
    }
}
