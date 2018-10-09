package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Meaning;
import seedu.address.model.person.Word;

/**
 * Pose a trivia question based on the person's word list
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
