package seedu.address.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * Deletes a deliveryman identified using it's displayed index from the address book.
 */
public class DeliverymanDeleteCommand extends DeliverymanCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = DeliverymanCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the deliveryman identified by the index number used in the displayed deliveryman list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DeliverymanCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DELIVERYMAN_SUCCESS = "Deleted Deliveryman: %1$s";

    private final Index targetIndex;

    public DeliverymanDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Deliveryman> lastShownList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        Deliveryman deliverymanToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (deliverymanToDelete.hasOrders()) {
            throw new CommandException(String.format(Messages.MESSAGE_DELIVERYMEN_HAS_ORDERS_CANNOT_DELETE,
                    deliverymanToDelete));
        }
        model.deleteDeliveryman(deliverymanToDelete);
        model.commitDeliverymenList();
        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERYMAN_SUCCESS, deliverymanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliverymanDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeliverymanDeleteCommand) other).targetIndex)); // state check
    }
}

