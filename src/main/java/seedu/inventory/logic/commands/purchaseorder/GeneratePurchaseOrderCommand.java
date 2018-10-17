package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Generate a purchase order for an item.
 */

public class GeneratePurchaseOrderCommand extends Command {

    public static final String COMMAND_WORD = "generate-po";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate a purchase order for an item. "
            + "Parameters: "
            + PREFIX_SKU + "SKU "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_REQDATE + "REQUIRED_DATE "
            + PREFIX_SUPPLIER + "SUPPLIER "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SKU + "apple-iphone-xr "
            + PREFIX_NAME + "iPhone XR "
            + PREFIX_QUANTITY + "32 "
            + PREFIX_REQDATE + "2018-12-12 "
            + PREFIX_SUPPLIER + "Apple Inc. ";

    public static final String MESSAGE_SUCCESS = "New purchase order generated: %1$s";

    private final PurchaseOrder toGenerate;

    /**
     * Creates an GeneratePurchaseOrderCommand to add the specified {@code PurchseOrder}
     */
    public GeneratePurchaseOrderCommand(PurchaseOrder po) {
        requireNonNull(po);
        toGenerate = po;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addPurchaseOrder(toGenerate);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toGenerate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GeneratePurchaseOrderCommand // instanceof handles nulls
                && toGenerate.equals(((GeneratePurchaseOrderCommand) other).toGenerate));
    }
}
