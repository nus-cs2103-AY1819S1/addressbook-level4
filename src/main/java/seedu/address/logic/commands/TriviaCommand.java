package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class TriviaCommand extends Command {

    public static final String COMMAND_WORD = "trivia";

    public static final String MESSAGE_SUCCESS = "Trivia ended";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        return null;
    }
}
