package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;
//@@author Harryqu123
/**
 * GroupAddCommand to delete the specified {@code Word}
 */
public class GroupaddCommand extends Command {

    public static final String COMMAND_WORD = "groupadd";
    public static final String MESSAGE_SUCCESS = "Group %1$s has been added";
    //@@author Harryqu123
    public static final String MESSAGE_USAGE = "please type in the group";
    public static final String MESSAGE_NO_GROUP = "The group typed has existed.";
    //@@author
    private final Tag toAdd;

    /**
     * Creates an GroupaddCommand to delete the specified {@code Word}
     */
    public GroupaddCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //@@author Harryqu123
        if (model.hasTag(toAdd)) {
            throw new CommandException(MESSAGE_NO_GROUP);
        }
        //@@author
        model.addGroup(toAdd);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupaddCommand // instanceof handles nulls
                && this.toAdd.equals(((GroupaddCommand) other).toAdd)); // state check
    }
}
//@@author
