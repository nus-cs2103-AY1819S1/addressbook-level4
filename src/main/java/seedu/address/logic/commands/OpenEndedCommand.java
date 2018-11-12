package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.test.openendedtest.OpenEndedTest;

/**
 * The OpenEndedCommand can only be used in a Open Ended Test.
 */

public class OpenEndedCommand extends Command {
    public static final String COMMAND_WORD = "";

    public static final String MESSAGE_USAGE = "Key in Y if you answered correctly, N if you did not";

    private static final String MESSAGE_ANSWER_SUCCESS = "Correct!";
    private static final String MESSAGE_ANSWER_FAILURE = "Wrong!";

    private final char in;

    public OpenEndedCommand(char in) {
        this.in = in;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert model.getCurrentRunningTest() instanceof OpenEndedTest;

        if (model.isOpenEndedTestAnswerCorrect(in)) {
            return new CommandResult(MESSAGE_ANSWER_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_ANSWER_FAILURE);
        }
    }
}
