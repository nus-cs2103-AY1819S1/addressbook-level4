package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 *  Answers the current trivia question
 */
public class TriviaAnsCommand extends Command {
    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_SUCCESS = "Correct! Type trivia to answer another question!";

    public static final String MESSAGE_WRONG = "Wrong! Try again or type trivia to get another question.";

    public static final String MESSAGE_FAILURE = "Please use the trivia command to get a question.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " your answer";

    private final String answer;

    public TriviaAnsCommand(String answer) {
        this.answer = answer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Word triviaQ = model.getTrivia();

        if (triviaQ == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        if (triviaQ.getName().toString().equals(answer)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_WRONG);
    }
}
