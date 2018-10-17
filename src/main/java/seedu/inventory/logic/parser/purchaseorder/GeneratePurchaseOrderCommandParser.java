package seedu.inventory.logic.parser.purchaseorder;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import java.util.stream.Stream;

import seedu.inventory.logic.commands.purchaseorder.GeneratePurchaseOrderCommand;
import seedu.inventory.logic.parser.ArgumentMultimap;
import seedu.inventory.logic.parser.ArgumentTokenizer;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.Prefix;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;


/**
 * Parses input arguments and creates a new GeneratePurchaseOrderCommand object
 */
public class GeneratePurchaseOrderCommandParser implements Parser<GeneratePurchaseOrderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GeneratePurchaseOrderCommand
     * and returns an GeneratePurchaseOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GeneratePurchaseOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SKU, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_REQDATE,
                        PREFIX_SUPPLIER);

        if (!arePrefixesPresent(argMultimap, PREFIX_SKU, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_REQDATE, PREFIX_SUPPLIER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GeneratePurchaseOrderCommand.MESSAGE_USAGE));
        }

        Sku sku = ParserUtil.parseSku(argMultimap.getValue(PREFIX_SKU).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        RequiredDate reqDate = ParserUtil.parseReqDate(argMultimap.getValue(PREFIX_REQDATE).get());
        Supplier supplier = ParserUtil.parseSupplier(argMultimap.getValue(PREFIX_SUPPLIER).get());
        PurchaseOrder.Status status = PurchaseOrder.Status.DEFAULT_STATUS;

        PurchaseOrder purchaseOrder = new PurchaseOrder(sku, name, quantity, reqDate, supplier, status);

        return new GeneratePurchaseOrderCommand(purchaseOrder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
