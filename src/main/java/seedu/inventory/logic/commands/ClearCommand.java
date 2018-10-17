package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;

/**
 * Clears the inventory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Inventory list has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new Inventory());
        model.commitInventory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
