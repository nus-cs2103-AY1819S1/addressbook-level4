package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;
//@@author Harryqu123
/**
 * GroupAddCommand to delete the specified {@code Word}
 */
public class GroupaddCommand extends Command {

    public static final String COMMAND_WORD = "groupadd";
    public static final String MESSAGE_SUCCESS = "Group %1$s has been added";

    private final Tag toAdd;

    /**
     * Creates an GroupaddCommand to delete the specified {@code Word}
     */
    public GroupaddCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.addGroup(toAdd);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
//@@author