package seedu.address.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERYMEN;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all deliverymen in the list to the user.
 */
public class DeliverymanListCommand extends DeliverymanCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all deliverymen";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredDeliverymenList(PREDICATE_SHOW_ALL_DELIVERYMEN);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
