package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.test.matchtest.exceptions.AnswerNotFoundException;
import seedu.address.model.test.matchtest.exceptions.QuestionNotFoundException;

/**
 * The MatchCommand can only be used in a Match Test.
 */
public class MatchCommand extends Command {
    public static final String COMMAND_WORD = "";

    public static final String MESSAGE_USAGE = "Match a question to its answer.\n"
            + "Parameters: INDEX_OF_QUESTION INDEX_OF_ANSWER \n"
            + "Example: 2 4\n"
            + "If the index of the question is not specified, the question at the top of its list will be matched.";

    public static final String MESSAGE_CORRECT_MATCH = "Perfect Match!";
    public static final String MESSAGE_WRONG_MATCH = "Wrong Match!";
    public static final String MESSAGE_QUESTION_NOT_FOUND = "The specified question is not found.";
    public static final String MESSAGE_ANSWER_NOT_FOUND = "The specified answer is not found.";

    private final Index questionIndex;
    private final Index answerIndex;

    public MatchCommand(Index questionIndex, Index answerIndex) {
        requireNonNull(answerIndex);
        this.questionIndex = questionIndex;
        this.answerIndex = answerIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert model.getCurrentRunningTest() instanceof MatchTest;

        try {
            if (model.matchQuestionAndAnswer(questionIndex, answerIndex)) {
                return new CommandResult(MESSAGE_CORRECT_MATCH);
            } else {
                return new CommandResult(MESSAGE_WRONG_MATCH);
            }
        } catch (QuestionNotFoundException e) {
            throw new CommandException(MESSAGE_QUESTION_NOT_FOUND);
        } catch (AnswerNotFoundException e) {
            throw new CommandException(MESSAGE_ANSWER_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        // All the tests are different even if they have the same parameters.
        return other == this
                || (other instanceof MatchCommand // instanceof handles nulls
                && (questionIndex == ((MatchCommand) other).questionIndex // handles null for questionIndex
                || questionIndex.equals(((MatchCommand) other).questionIndex))
                && answerIndex.equals(((MatchCommand) other).answerIndex));
    }
}
