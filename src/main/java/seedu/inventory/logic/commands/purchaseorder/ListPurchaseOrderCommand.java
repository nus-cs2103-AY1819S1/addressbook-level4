package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.model.Model;


/**
 * Lists all pending purchase order in the system to the user.
 */
public class ListPurchaseOrderCommand extends Command {

    public static final String COMMAND_WORD = "list-po";

    public static final String MESSAGE_SUCCESS = "Listed all pending purchase order";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.viewPurchaseOrder();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
