package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. Input date and "
            + "time values with respect to a single day only. For events occurring over two days, add them into the "
            + "address book separately."
            + "Parameters: "
            + PREFIX_NAME + "EVENT NAME "
            + PREFIX_EVENT_DESCRIPTION + "EVENT DESCRIPTION "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START TIME "
            + PREFIX_END_TIME + "END TIME "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_INDEX + "CONTACT INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Doctor appointment "
            + PREFIX_EVENT_DESCRIPTION + "consultation "
            + PREFIX_DATE + "2018-09-18 "
            + PREFIX_START_TIME + "1030 "
            + PREFIX_END_TIME + "1230 "
            + PREFIX_ADDRESS + "123, Clementi Rd, 1234665 "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final String MESSAGE_CLASHING_EVENT = "This event clashes with another event in the address book";

    private final Event toAdd;
    private final Set<Index> contactIndicesToAdd;

    /**
     * Creates an AddEventCommand to add the specified Event {@code Event}.
     * The {@code event} should not be a null object, and {@code indices} should not be null or contain any nulls.
     */
    public AddEventCommand(Event event, Set<Index> indices) {
        requireNonNull(event);
        requireAllNonNull(indices);

        toAdd = event;
        contactIndicesToAdd = indices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (model.hasClashingEvent(toAdd)) {
            throw new CommandException(MESSAGE_CLASHING_EVENT);
        }

        // set the list of Person objects as the eventContacts of the event to be added
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        if (contactIndicesToAdd.stream()
                .anyMatch(targetIndex -> targetIndex.getZeroBased() >= lastShownPersonList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Set<Person> contactsToAdd = IntStream.range(0, lastShownPersonList.size())
                .filter(index -> contactIndicesToAdd.contains(Index.fromZeroBased(index)))
                .mapToObj(lastShownPersonList::get)
                .collect(Collectors.toSet());

        toAdd.setEventContacts(contactsToAdd);

        model.addEvent(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd)); // state check
    }
}
