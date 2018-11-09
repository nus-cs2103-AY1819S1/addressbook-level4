package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Adds an event tag to the address book, if it does not already exist in the address book.
 */
public class AddEventTagCommand extends Command {

    public static final String COMMAND_WORD = "addEventTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event tag to the address book. "
            + "Parameters: "
            + PREFIX_TAG + "EVENT TAG "
            + "[" + PREFIX_TAG + "EVENT TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "Meeting";

    public static final String MESSAGE_SUCCESS = "New event tags added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "The event tag(s) %1$s already exists in the address book";

    private final Set<Tag> toAdd;

    public AddEventTagCommand(Set<Tag> eventTags) {
        requireAllNonNull(eventTags);
        toAdd = eventTags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Set<Tag> duplicateTags = toAdd.stream()
                .filter(model::hasEventTag)
                .collect(Collectors.toSet());

        if (!duplicateTags.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, duplicateTags));
        }

        for (Tag eventTag : toAdd) {
            model.addEventTag(eventTag);
        }
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventTagCommand // instanceof handles nulls
                && toAdd.equals(((AddEventTagCommand) other).toAdd));
    }
}
