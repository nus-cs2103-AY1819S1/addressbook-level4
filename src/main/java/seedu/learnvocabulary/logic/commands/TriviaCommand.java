package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.model.Model.PREDICATE_SHOW_ALL_WORDS;

import java.util.List;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Word;

/**
 * Sets vocabulary book to trivia mode
 */

public class TriviaCommand extends Command {

    public static final String COMMAND_WORD = "trivia";

    public static final String MESSAGE_SUCCESS = "Trivia started!\n";

    public static final String MESSAGE_FAIL = "Vocabulary list is empty. Please add words in use trivia.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Outputs a question with the meaning of a word.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);
        List<Word> lastShownList = model.getFilteredWordList();

        if (lastShownList.size() < 1) {
            return new CommandResult(MESSAGE_FAIL);
        }

        model.toggleTriviaMode();
        model.setTriviaList();
        Word triviaQ = model.getTrivia();
        Meaning qMeaning = triviaQ.getMeaning();

        return new CommandResult(MESSAGE_SUCCESS + "Question: " + qMeaning.toString());




    }
}
