package seedu.restaurant.testutil.sales;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_QUANTITY_SOLD;

import seedu.restaurant.logic.commands.sales.EditSalesCommand.EditRecordDescriptor;
import seedu.restaurant.logic.commands.sales.RecordSalesCommand;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * A utility class for {@code SalesRecord}.
 */
public class RecordUtil {
    /**
     * Returns a record-sales command string for adding the {@code record}.
     */
    public static String getRecordSalesCommand(SalesRecord record) {
        return RecordSalesCommand.COMMAND_WORD + " " + getRecordDetails(record);
    }
    /**
     * Returns the part of command string for the given {@code record}'s details.
     */
    public static String getRecordDetails(SalesRecord record) {
        return PREFIX_DATE + record.getDate().toString() + " "
                + PREFIX_ITEM_NAME + record.getName().toString() + " "
                + PREFIX_QUANTITY_SOLD + record.getQuantitySold().toString() + " "
                + PREFIX_ITEM_PRICE + record.getPrice().toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecordDescriptor}'s details.
     */
    public static String getEditRecordDescriptorDetails(EditRecordDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.toString()).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_ITEM_NAME).append(name.toString()).append(" "));
        descriptor.getQuantitySold().ifPresent(quantitySold -> sb.append(PREFIX_QUANTITY_SOLD)
                .append(quantitySold.toString()).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_ITEM_PRICE).append(price.toString()).append(" "));
        return sb.toString();
    }
}
