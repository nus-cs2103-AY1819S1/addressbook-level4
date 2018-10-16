package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_LOCATION + "LOCATION "
            + PREFIX_EVENT_START_DATE + "START DATE "
            + PREFIX_EVENT_END_DATE + "END DATE "
            + PREFIX_EVENT_START_TIME + "START TIME "
            + PREFIX_EVENT_END_TIME + "END TIME "
            + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Youth Humanitarian Challenge "
            + PREFIX_EVENT_LOCATION + "29 Havelock Road "
            + PREFIX_EVENT_START_DATE + "28-09-2018 "
            + PREFIX_EVENT_END_DATE + "28-09-2018 "
            + PREFIX_EVENT_START_TIME + "10:00 "
            + PREFIX_EVENT_END_TIME + "14:00 "
            + PREFIX_EVENT_DESCRIPTION + "To engage youths in humanitarianism. "
            + PREFIX_TAG + "Competition ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
