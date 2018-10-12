package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 * Deletes a word identified using it's displayed index from the learnvocabulary book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the word identified by the index number used in the displayed word list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_WORD_SUCCESS = "Deleted Word: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Word> lastShownList = model.getFilteredWordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
        }

        Word wordToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteWord(wordToDelete);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_DELETE_WORD_SUCCESS, wordToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
