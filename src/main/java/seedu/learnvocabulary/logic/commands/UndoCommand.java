package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.model.Model.PREDICATE_SHOW_ALL_WORDS;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;

/**
 * Reverts the {@code model}'s LearnVocabulary to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoLearnVocabulary()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoLearnVocabulary();
        model.updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
