package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.model.Model.PREDICATE_SHOW_ALL_WORDS;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;

/**
 * Reverts the {@code model}'s learnvocabulary book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoLearnVocabulary()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoLearnVocabulary();
        model.updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
