package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 * Displays the word of the day at Dictionary.com
 */
public class WordOfTheDayCommand extends Command {
    public static final String COMMAND_WORD = "word";

    public static final String MESSAGE_SUCCESS = "Today's word on Dictionary.com: %1$s\n";

    private final Word wordOfTheDay;

    public WordOfTheDayCommand(Word word) {
        requireNonNull(word);
        wordOfTheDay = word;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, wordOfTheDay));
    }
}
