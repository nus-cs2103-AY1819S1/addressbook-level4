package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.shared.Title;



/**
 * Remove a person from a group existed in the address book.
 */
public class LeaveCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from a group in the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GROUP + "GROUP\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Derek Hardy "
            + PREFIX_GROUP + "GROUP_03";

    public static final String MESSAGE_LEAVE_SUCCESS = "Person: %1$s removed from the group: %1$s";

    private final Name personName;
    private final Title groupName;
    private Person matchedPersonByName;
    private Group matchedGroupByName;

    /**
     * Creates an LeaveCommand to remove the specified {@code person} from {@code group}.
     */
    public LeaveCommand(Person person, Group group) {
        requireAllNonNull(person);
        this.personName = person.getName();
        this.groupName = group.getTitle();
        matchedPersonByName = null;
        matchedGroupByName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        matchedGroupByName = model.getGroupByTitle(groupName);

        matchedPersonByName = model.getPersonByName(personName);

        if (matchedGroupByName == null) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);

        } else if (matchedPersonByName == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        model.leaveGroup(matchedPersonByName, matchedGroupByName);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_LEAVE_SUCCESS, matchedPersonByName, matchedGroupByName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveCommand // instanceof handles nulls
                && personName.equals(((LeaveCommand) other).personName)
                && groupName.equals(((LeaveCommand) other).groupName)); // state check
    }
}
