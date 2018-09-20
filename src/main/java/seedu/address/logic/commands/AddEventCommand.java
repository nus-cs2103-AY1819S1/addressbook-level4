package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds a person to the address book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the scheduler. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + "[" + PREFIX_EVENT_START_DATE_TIME + "DATETIME in natural language] "
            + "[" + PREFIX_EVENT_END_DATE_TIME + "DATETIME in natural language] "
            + "[" + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION]\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Study with me "
            + PREFIX_EVENT_START_DATE_TIME + "today 5pm "
            + PREFIX_EVENT_END_DATE_TIME + "tomorrow 3am "
            + PREFIX_EVENT_DESCRIPTION + "Studying time "
            + PREFIX_TAG + "study "
            + PREFIX_TAG + "ad-hoc";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

//        if (model.hasPerson(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.addPerson(toAdd);
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
