package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;
//@@author Harryqu123
/**
 * Shows the list of all of the group tags
 */
public class ShowGroupCommand extends Command {

    public static final String COMMAND_WORD = "showgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all words"
            + "which exist inside the given group.\n"
            + "Parameters: GROUP NAME \n"
            + "Example: " + COMMAND_WORD + "toLearn";

    public static final String MESSAGE_SUCCESS = "Group %1$s has been shown";

    private final TagContainsKeywordsPredicate predicate;

    /**
     * Creates an GroupaddCommand to delete the specified {@code Word}
     */
    public ShowGroupCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (predicate.getTag().isEmpty()) {
            Set<Tag> totalTag = model.getTags();
            String result = "The existed Word Group that word can be added are: \n";
            int index = 0;
            for (Tag tag: totalTag) {
                result = result + tag.tagName + ",";
                index++;
                if (index % 4 == 0) {
                    result += "\n";
                }
            }
            return new CommandResult(result);
        }
        model.updateTag(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, predicate.getTag()));
    }
}
//@@author
