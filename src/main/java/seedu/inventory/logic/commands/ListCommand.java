package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;

/**
 * Lists all items in the inventory to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.viewItem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
