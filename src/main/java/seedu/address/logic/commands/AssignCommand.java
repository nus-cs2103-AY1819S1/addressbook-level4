package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYMAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.exceptions.OrdersLimitExceededException;
import seedu.address.model.order.Order;

/**
 * Assigns multiple orders to a deliveryman with two way association.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "/assign";

    public static final String MESSAGE_SUCCESS = "Assigned order %1$s successfully to deliveryman %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign orders to deliveryman. "
            + "Parameters: "
            + PREFIX_DELIVERYMAN + "DELIVERYMAN_ID "
            + PREFIX_ORDER + "ORDER_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DELIVERYMAN + "1 "
            + PREFIX_ORDER + "1 " + PREFIX_ORDER + "3";

    private final Index deliverymanId;
    private final Set<Index> orderIds;

    /**
     * Creates an AssignCommand to add the specified {@code user}
     */
    public AssignCommand(Index deliverymanId, Set<Index> orderIds) {
        requireAllNonNull(deliverymanId, orderIds);
        this.deliverymanId = deliverymanId;
        this.orderIds = orderIds;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Deliveryman> lastShownDeliverymanList = model.getFilteredDeliverymenList();
        List<Order> lastShownOrderList = model.getFilteredOrderList();
        Set<Order> ordersToAdd = new HashSet<>();

        if (deliverymanId.getZeroBased() >= lastShownDeliverymanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }
        Deliveryman deliverymanToAssign = lastShownDeliverymanList.get(deliverymanId.getZeroBased());

        for (Index i : orderIds) {
            if (i.getZeroBased() >= lastShownOrderList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
            }
            Order order = lastShownOrderList.get(i.getZeroBased());
            if (order.isCompleted()) {
                throw new CommandException(Messages.MESSAGE_COMPLETED_ORDER);
            }

            if (order.isAlreadyAssignedDeliveryman()) {
                throw new CommandException(String.format(Messages.MESSAGE_ORDER_ALREADY_ASSIGNED_TO_DELIVERYMAN,
                        i.getOneBased(), order.getDeliveryman()));
            }
            ordersToAdd.add(order);
        }

        Deliveryman assignedDeliveryman = new Deliveryman(deliverymanToAssign);
        if (!assignedDeliveryman.canAccommodate(ordersToAdd)) {
            throw new CommandException(Messages.MESSAGE_ORDERS_LIMIT_EXCEEDED);
        }

        for (Order order : ordersToAdd) {
            Order updatedOrder = new Order(order);
            try {
                updatedOrder.setDeliveryman(assignedDeliveryman);
            } catch (OrdersLimitExceededException e) {
                throw new CommandException(Messages.MESSAGE_ORDERS_LIMIT_EXCEEDED);
            }
            model.updateOrder(order, updatedOrder);
        }

        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);
        model.commitOrderBook();

        model.updateDeliveryman(deliverymanToAssign, assignedDeliveryman);
        model.updateFilteredDeliverymenList(Model.PREDICATE_SHOW_ALL_DELIVERYMEN);
        model.commitDeliverymenList();

        StringJoiner validSj = new StringJoiner(", ");
        for (Index i : orderIds) {
            validSj.add(Integer.toString(i.getOneBased()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, validSj.toString(), assignedDeliveryman));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && deliverymanId.equals(((AssignCommand) other).deliverymanId)
                && orderIds.equals(((AssignCommand) other).orderIds));
    }
}
