package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;
//@@author Harryqu123
/**
 * Creates an GroupdeleteCommand to delete the specified {@code Word}
 */
public class GroupdeleteCommand extends Command {

    public static final String COMMAND_WORD = "groupdelete";
    public static final String MESSAGE_NO_GROUP = "The group typed does not exist.";
    public static final String MESSAGE_SUCCESS = "Group %1$s has been deleted";

    private final Tag toDelete;

    /**
     * Creates an GroupdeleteCommand to delete the specified {@code Word}
     */
    public GroupdeleteCommand(Tag tag) {
        requireNonNull(tag);
        toDelete = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(toDelete)) {
            throw new CommandException(MESSAGE_NO_GROUP);
        }

        model.deleteGroup(toDelete);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupdeleteCommand // instanceof handles nulls
                && this.toDelete.equals(((GroupdeleteCommand) other).toDelete)); // state check
    }
}
//@@author
