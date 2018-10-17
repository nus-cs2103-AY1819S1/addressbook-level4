package seedu.inventory.testutil.purchaseorder;

import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.inventory.logic.commands.purchaseorder.GeneratePurchaseOrderCommand;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * A utility class for PurchaseOrder.
 */
public class PurchaseOrderUtil {
    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getGeneratePoCommand(PurchaseOrder po) {
        return GeneratePurchaseOrderCommand.COMMAND_WORD + " " + getPurchaseOrderDetails(po);
    }

    /**
     * Returns the part of command string for the given {@code po}'s details.
     */
    public static String getPurchaseOrderDetails(PurchaseOrder po) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_SKU + po.getSku().value + " ");
        sb.append(PREFIX_NAME + po.getName().fullName + " ");
        sb.append(PREFIX_QUANTITY + po.getQuantity().value + " ");
        sb.append(PREFIX_REQDATE + po.getReqDate().requiredDate + " ");
        sb.append(PREFIX_SUPPLIER + po.getSupplier().supplierName + " ");
        return sb.toString();
    }
}
