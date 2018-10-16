package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.test.matchtest.MatchTest;

/**
 * The MatchCommand can only be used in a Matching test.
 */
public class MatchCommand extends Command {
    public static final String COMMAND_WORD = "";

    public static final String MESSAGE_USAGE = "Match a question to its answer. "
            + "Parameters: INDEX_OF_QUESTION INDEX_OF_ANSWER \n"
            + "Example: 1 4";

    public static final String MESSAGE_MATCH_SUCCESS = "Perfect Match!";
    public static final String MESSAGE_MATCH_FAILURE = "Wrong Match!";
    public static final String MESSAGE_INDEX_OUT_OF_BOUND = "The index specified is out of bound.";

    private final Index questionIndex;
    private final Index answerIndex;

    public MatchCommand(Index questionIndex, Index answerIndex) {
        this.questionIndex = questionIndex;
        this.answerIndex = answerIndex;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert model.getCurrentRunningTest() instanceof MatchTest;

        try {
            if (model.matchQuestionAndAnswer(questionIndex, answerIndex)) {
                return new CommandResult(String.format(MESSAGE_MATCH_SUCCESS));
            } else {
                throw new CommandException(MESSAGE_MATCH_FAILURE);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        // All the tests are different even if they have the same parameters.
        return other == this
                || (other instanceof MatchCommand // instanceof handles nulls
                && questionIndex.equals(((MatchCommand) other).questionIndex)
                && answerIndex.equals(((MatchCommand) other).answerIndex));
    }
}
