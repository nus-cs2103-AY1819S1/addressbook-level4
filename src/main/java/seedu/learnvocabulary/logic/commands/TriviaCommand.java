package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Word;

/**
 * Pose a trivia question based on the word's word list
 */

public class TriviaCommand extends Command {

    public static final String COMMAND_WORD = "trivia";

    public static final String MESSAGE_SUCCESS = "Question: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setTrivia();
        Word triviaQ = model.getTrivia();
        Meaning qMeaning = triviaQ.getMeaning();

        return new CommandResult(MESSAGE_SUCCESS + qMeaning.toString());
    }
}
