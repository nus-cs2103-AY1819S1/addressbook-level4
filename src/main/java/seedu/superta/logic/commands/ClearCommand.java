package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.SuperTaClient;

/**
 * Clears the SuperTA client.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Database has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new SuperTaClient());
        model.commitSuperTaClient();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
