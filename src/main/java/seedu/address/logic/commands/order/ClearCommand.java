package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OrderBook;
import seedu.address.model.order.Order;

/**
 * Clears the address book.
 */
public class ClearCommand extends OrderCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Order book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        for (Order order : model.getOrderBook().getOrderList()) {
            if (order.isOngoing()) {
                throw new CommandException(Messages.MESSAGE_ORDER_ONGOING_CANNOT_CLEAR);
            }
        }
        model.resetData(new OrderBook());
        model.commitOrderBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
