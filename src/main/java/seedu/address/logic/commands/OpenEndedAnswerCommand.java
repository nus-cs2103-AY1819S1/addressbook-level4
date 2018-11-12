package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.test.openendedtest.OpenEndedTest;

/**
 * The command that will record the answer for the user's attempt
 */
public class OpenEndedAnswerCommand extends Command {
    private static String userAnswer;

    private static final String MESSAGE_ANSWER_RECORDED = "Answer recorded!\n" + "Please compare your answer and enter"
            + "y if it is correct, n if it is not";

    public OpenEndedAnswerCommand(String userInput) {
        this.userAnswer = userInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        assert model.getCurrentRunningTest() instanceof OpenEndedTest;
        model.recordAnswerToOpenEndedTest(userAnswer);
        return new CommandResult(MESSAGE_ANSWER_RECORDED);
    }

}
