package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Composes email and writes it to hard disk
 */
public class ComposeCommand extends Command {

    public static final String COMMAND_WORD = "compose";

    public static final String MESSAGE_SUCCESS = "Email composed";

    public ComposeCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
