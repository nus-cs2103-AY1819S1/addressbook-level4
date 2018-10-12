package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.learnvocabulary.commons.core.EventsCenter;
import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.commons.events.ui.JumpToListRequestEvent;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 * Selects a word identified using it's displayed index from the learnvocabulary book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the word identified by the index number used in the displayed word list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_WORD_SUCCESS = "Selected Word: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Word> filteredWordList = model.getFilteredWordList();

        if (targetIndex.getZeroBased() >= filteredWordList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_WORD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
