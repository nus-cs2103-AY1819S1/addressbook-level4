package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_STOCK_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_PER_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMedicineCommand;
import seedu.address.logic.commands.EditMedicineCommand.MedicineDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMedicineCommand object
 */
public class EditMedicineCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommandMedicine
     * and returns an EditCommandMedicine object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMedicineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_MINIMUM_STOCK_QUANTITY,
                        PREFIX_PRICE_PER_UNIT, PREFIX_SERIAL_NUMBER, PREFIX_STOCK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMedicineCommand.MESSAGE_USAGE), pe);
        }

        MedicineDescriptor medicineDescriptor = new MedicineDescriptor();
        if (argMultimap.getValue(PREFIX_MEDICINE_NAME).isPresent()) {
            medicineDescriptor.setMedicineName(
                    ParserUtil.parseMedicineName(argMultimap.getValue(PREFIX_MEDICINE_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_MINIMUM_STOCK_QUANTITY).isPresent()) {
            medicineDescriptor.setMinimumStockQuantity(
                    ParserUtil.parseMinimumStockQuantity(argMultimap.getValue(PREFIX_MINIMUM_STOCK_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE_PER_UNIT).isPresent()) {
            medicineDescriptor.setPricePerUnit(
                    ParserUtil.parsePricePerUnit(argMultimap.getValue(PREFIX_PRICE_PER_UNIT).get()));
        }
        if (argMultimap.getValue(PREFIX_SERIAL_NUMBER).isPresent()) {
            medicineDescriptor.setSerialNumber(
                    ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_MINIMUM_STOCK_QUANTITY).isPresent()) {
            medicineDescriptor.setStock(
                    ParserUtil.parseStock(argMultimap.getValue(PREFIX_MINIMUM_STOCK_QUANTITY).get()));
        }

        if (!medicineDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMedicineCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMedicineCommand(index, medicineDescriptor);
    }

}
