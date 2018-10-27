package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.PersonFinderUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

//@@author zioul123
/**
 * Deletes a person identified using their name from the address book.
 */
public class DeleteByNameCommand extends DeleteCommand {

    protected final String personIdentifier;

    public DeleteByNameCommand(String personIdentifier) {
        super(Index.fromZeroBased(NO_INDEX));
        requireNonNull(personIdentifier);

        this.personIdentifier = personIdentifier;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToDelete;
        try {
            personToDelete = PersonFinderUtil.findPerson(model, personIdentifier);
        } catch (ParseException pe) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        model.deletePerson(personToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && personIdentifier.equals(((DeleteByNameCommand) other).personIdentifier)); // state check
    }
}
