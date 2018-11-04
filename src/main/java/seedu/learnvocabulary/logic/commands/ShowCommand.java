package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.learnvocabulary.commons.core.EventsCenter;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.commons.events.ui.JumpToListRequestEvent;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;
import seedu.learnvocabulary.model.word.Word;

/**
 * Finds and lists all words in LearnVocabulary whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    private static final int FIRST_INDEX = 1;
    public static final String COMMAND_NO_WORDS_FOUND = "No words have been found! Please try again!\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " rainstorm hurricane tsunami";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all words whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " rainstorm hurricane tsunami";

    private final NameContainsKeywordsPredicate predicate;

    public ShowCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWordList(predicate);
        List<Word> lastShownList = model.getFilteredWordList();
        if (lastShownList.size() != 0) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(Index.fromOneBased(FIRST_INDEX)));
            return new CommandResult(toString(lastShownList));
        } else {
            return new CommandResult(COMMAND_NO_WORDS_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && predicate.equals(((ShowCommand) other).predicate)); // state check
    }

    /**
     * Returns a properly formatted String just for show command.
     */
    public static String toString(List<Word> lastShownList) {
        StringBuilder sb = new StringBuilder();
        for (Word word: lastShownList) {
            sb.append("Word: " + word.getName() + " \n");
            sb.append("Meaning: " + word.getMeaning() + "\n");
            sb.append("Tags: " + word.getTags().toString() + "\n");
        }
        return sb.toString();
    }
}
