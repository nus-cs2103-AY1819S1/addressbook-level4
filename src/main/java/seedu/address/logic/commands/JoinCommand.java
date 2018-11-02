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
 * Join a person into a group existed in the address book.
 */
public class JoinCommand extends Command {

    public static final String COMMAND_WORD = "join";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person into a group in the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GROUP + "GROUP\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Derek Hardy "
            + PREFIX_GROUP + "GROUP_03";

    public static final String MESSAGE_JOIN_SUCCESS = "Person: %1$s added to the group: %2$s";
    public static final String MESSAGE_PERSON_ALREADY_IN_GROUP = "Person is already in group";

    private final Name personName;
    private final Title groupName;
    private Person matchedPersonByName;
    private Group matchedGroupByName;

    /**
     * Creates an JoinCommand to join the specified {@code person} into {@code group}.
     */
    public JoinCommand(Person person, Group group) {
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

        if (matchedGroupByName.hasMember(matchedPersonByName)) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_IN_GROUP);
        }

        model.joinGroup(matchedPersonByName, matchedGroupByName);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_JOIN_SUCCESS, matchedPersonByName.getName().toString(),
            matchedGroupByName.getTitle().fullTitle));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JoinCommand // instanceof handles nulls
                && personName.equals(((JoinCommand) other).personName)
                && groupName.equals(((JoinCommand) other).groupName)); // state check
    }
}
