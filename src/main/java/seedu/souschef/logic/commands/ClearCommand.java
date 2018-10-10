package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new AppContent());
        model.commitAppContent();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
