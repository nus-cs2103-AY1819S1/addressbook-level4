package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.HealthBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar) {
        requireNonNull(model);
        model.resetData(new HealthBook());
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
