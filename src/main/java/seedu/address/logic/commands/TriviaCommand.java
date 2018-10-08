package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Word;

public class TriviaCommand extends Command {

    public static final String COMMAND_WORD = "trivia";

    public static final String MESSAGE_SUCCESS = "Question: ";

    private ObservableList<Word> triviaList;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Word triviaQ = model.getTrivia();

        return new CommandResult(String.format(MESSAGE_SUCCESS, triviaQ.getMeaning()));
    }
}
