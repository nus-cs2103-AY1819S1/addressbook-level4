package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_STOCK_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_PER_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Stock;

/**
 * Parses input arguments and creates a new AddMedicineCommand object
 */
public class AddMedicineCommandParser implements Parser<AddMedicineCommand> {
    @Override
    public AddMedicineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_MINIMUM_STOCK_QUANTITY,
                        PREFIX_PRICE_PER_UNIT, PREFIX_SERIAL_NUMBER, PREFIX_STOCK);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE_NAME, PREFIX_MINIMUM_STOCK_QUANTITY,
                PREFIX_PRICE_PER_UNIT, PREFIX_SERIAL_NUMBER, PREFIX_STOCK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedicineCommand.MESSAGE_USAGE));
        }

        MedicineName name = ParserUtil.parseMedicineName(argMultimap.getValue(PREFIX_MEDICINE_NAME).get());
        MinimumStockQuantity minimumStockQuantity = ParserUtil.parseMinimumStockQuantity(argMultimap.getValue(
                PREFIX_MINIMUM_STOCK_QUANTITY).get());
        PricePerUnit pricePerUnit = ParserUtil.parsePricePerUnit(argMultimap.getValue(PREFIX_PRICE_PER_UNIT).get());
        SerialNumber serialNumber = ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUMBER).get());
        Stock stock = ParserUtil.parseStock(argMultimap.getValue(PREFIX_STOCK).get());

        Medicine medicine = new Medicine(name, minimumStockQuantity, pricePerUnit, serialNumber, stock);

        return new AddMedicineCommand(medicine);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
