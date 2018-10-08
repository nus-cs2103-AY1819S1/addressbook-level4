package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Word;
import seedu.address.model.person.Meaning;

public class TriviaCommand extends Command {

    public static final String COMMAND_WORD = "trivia";

    public static final String MESSAGE_SUCCESS = "Question: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Word triviaQ = model.getTrivia();
        Meaning QMeaning = triviaQ.getMeaning();

        return new CommandResult(MESSAGE_SUCCESS + QMeaning.toString());
    }
}
