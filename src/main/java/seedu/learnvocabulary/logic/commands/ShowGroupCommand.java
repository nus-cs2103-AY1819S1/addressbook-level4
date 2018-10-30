package seedu.learnvocabulary.logic.commands;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

public class ShowGroupCommand extends Command {

    public static final String COMMAND_WORD = "showgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all words which are inside the give group.\n"
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
        model.updateTag(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, predicate.getTag()));
    }
}
