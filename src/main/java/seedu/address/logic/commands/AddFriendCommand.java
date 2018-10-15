package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class AddFriendCommand extends Command {

    public static final String COMMAND_WORD = "addFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": For two persons identified by the index number used in the displayed person list, "
            + "add friend for each other.\n"
            + "Parameters: INDEX,INDEX (both must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1,2";

    public static final String MESSAGE_ADD_FRIEND_SUCCESS = "Friends added: %1%2$s";

    private final Index targetIndex1;
    private final Index targetIndex2;

    public AddFriendCommand(Index targetIndex1, Index targetIndex2) {
        this.targetIndex1 = targetIndex1;
        this.targetIndex2 = targetIndex2;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex1.getZeroBased() >= lastShownList.size()
                || targetIndex2.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddFriend1 = lastShownList.get(targetIndex1.getZeroBased());
        Person personToAddFriend2 = lastShownList.get(targetIndex2.getZeroBased());
        model.addFriends(personToAddFriend1, personToAddFriend2);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_FRIEND_SUCCESS, personToAddFriend1,
                personToAddFriend2));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFriendCommand // instanceof handles nulls
                && targetIndex1.equals(((AddFriendCommand) other).targetIndex1) // state check
                && targetIndex2.equals(((AddFriendCommand) other).targetIndex2));
    }
}
