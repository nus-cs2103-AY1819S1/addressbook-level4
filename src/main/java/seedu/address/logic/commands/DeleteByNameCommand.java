package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

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
}
