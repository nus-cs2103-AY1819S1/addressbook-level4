package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Word;


public class TriviaAnsCommand {
    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_SUCCESS = "Correct! Type trivia to answer another question!";

    public static final String MESSAGE_FAILURE = "Wrong! Try again or type trivia to get another question.";

    private final String answer;

    public TriviaAnsCommand(String answer) {
        this.answer = answer;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Word triviaQ = model.getTrivia();

        if (triviaQ.getName().toString().equals(answer)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }

        return new CommandResult(MESSAGE_FAILURE);
    }
}
