package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_QUANTITY_SOLD;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.restaurant.logic.commands.sales.RecordSalesCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * Parses input arguments and creates a new RecordSalesCommand object
 */
public class RecordSalesCommandParser implements Parser<RecordSalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordSalesCommand and returns a
     * RecordSalesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordSalesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD, PREFIX_ITEM_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD, PREFIX_ITEM_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordSalesCommand.MESSAGE_USAGE));
        }

        Date date = SalesParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        ItemName name = SalesParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get());
        QuantitySold quantitySold = SalesParserUtil.parseQuantitySold(argMultimap.getValue(PREFIX_QUANTITY_SOLD).get());
        Price price = SalesParserUtil.parsePrice(argMultimap.getValue(PREFIX_ITEM_PRICE).get());

        SalesRecord record = new SalesRecord(date, name, quantitySold, price);

        return new RecordSalesCommand(record);
    }
}
